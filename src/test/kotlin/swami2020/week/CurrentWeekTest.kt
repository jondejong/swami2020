package swami2020.week

import org.http4k.core.Method
import org.http4k.core.Status
import swami2020.SecureRequest
import swami2020.api.weekLens
import kotlin.test.Test
import kotlin.test.assertEquals

class CurrentWeekTest : BaseWeekTest() {

    companion object {

        val currentUrl = "$weeksUrl/current"

    }

    @Test
    fun fetchCurrentReadyWeek() {
        // If a week is ready, but not completed, then it's current
        setReadyCurrentWeek(2)
        val response = client(SecureRequest(Method.GET, currentUrl, userToken))
        assertEquals(
                expected = Status.OK,
                actual = response.status
        )

        assertEquals(
                expected = weekIds[2],
                actual = weekLens(response).id
        )
    }

    @Test
    fun fetchCurrentCompletedWeek() {
        //If no week is ready, but not completed, then the last completed week is current
        setCompletedCurrentWeek(3)
        val response = client(SecureRequest(Method.GET, currentUrl, userToken))
        assertEquals(
                expected = Status.OK,
                actual = response.status
        )

        assertEquals(
                expected = weekIds[3],
                actual = weekLens(response).id
        )
    }


    @Test
    fun noCurrentWeek() {
        //if no weeks match the criteria, we should get a 404
        resetWeeks()
        val response = client(SecureRequest(Method.GET, currentUrl, userToken))
        assertEquals(
                expected = Status.NOT_FOUND,
                actual = response.status
        )
    }
}