package swami2020.week

import org.http4k.core.Method
import org.http4k.core.Status
import swami2020.SecureRequest
import swami2020.TestUtil
import kotlin.test.Test
import kotlin.test.assertEquals

class CurrentWeekTest : BaseWeekTest() {

    companion object {

        private val service = TestUtil.appFactory.weekService

        val currentUrl = "$weeksUrl/current"

        val setReadyCurrentWeek = { week: Int ->
            // The "current" week is ready but not completed
            cleanUp()
            for (number in 1.until(week)) {
                service.updateReady(
                        id = ids[number]!!,
                        status = true
                )
                service.updateComplete(
                        id = ids[number]!!,
                        status = true
                )
            }
            service.updateReady(
                    id = ids[week]!!,
                    status = true
            )
        }

        val setCompletedCurrentWeek = { week: Int ->
            // The "current" week is ready but not completed
            cleanUp()
            for (number in 1.until(week + 1)) {
                service.updateReady(
                        id = ids[number]!!,
                        status = true
                )
                service.updateComplete(
                        id = ids[number]!!,
                        status = true
                )
            }
        }
    }

    @Test
    fun fetchCurrentReadyWeek() {
        // If a week is ready, but not completed, then it's current
        setReadyCurrentWeek(2)
        val response = client(SecureRequest(Method.GET, currentUrl, token))
        assertEquals(
                expected = Status.OK,
                actual = response.status
        )

        assertEquals(
                expected = ids[2],
                actual = weekLens(response).id
        )
    }

    @Test
    fun fetchCurrentCompletedWeek() {
        //If no week is ready, but not completed, then the last completed week is current
        setCompletedCurrentWeek(3)
        val response = client(SecureRequest(Method.GET, currentUrl, token))
        assertEquals(
                expected = Status.OK,
                actual = response.status
        )

        assertEquals(
                expected = ids[3],
                actual = weekLens(response).id
        )
    }


    @Test
    fun noCurrentWeek() {
        //if no weeks match the criteria, we should get a 404
        cleanUp()
        val response = client(SecureRequest(Method.GET, currentUrl, token))
        assertEquals(
                expected = Status.NOT_FOUND,
                actual = response.status
        )
    }
}