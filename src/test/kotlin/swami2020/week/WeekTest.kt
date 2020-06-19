package swami2020.week

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import swami2020.SecureRequest
import swami2020.api.createWeekLens
import swami2020.api.request.CreateWeek
import swami2020.api.request.UpdateWeekComplete
import swami2020.api.request.UpdateWeekReady
import swami2020.api.updateWeekCompleteLens
import swami2020.api.updateWeekReadyLens
import swami2020.api.weekListLens
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WeekTest : BaseWeekTest() {

    @Test
    fun createAndListWeeks() {
        val actual = client(SecureRequest(Method.GET, weeksUrl, userToken))
        assertEquals(Status.OK, actual.status)

        val weeks = weekListLens(actual)
        assertEquals(5, weeks.size)

        assertEquals(
                expected = Status.OK,
                actual = client(
                        createWeekLens(
                                CreateWeek(6),
                                SecureRequest(Method.POST, weeksUrl, userToken)
                        )
                ).status
        )

        assertEquals(
                expected = 6,
                actual = weekListLens(
                        client(SecureRequest(Method.GET, weeksUrl, userToken))
                ).size
        )
    }

    @Test
    fun markWeekComplete() {
        assertEquals(
                expected = Status.OK,
                actual = client(
                        updateWeekCompleteLens(
                                UpdateWeekComplete(true),
                                SecureRequest(Method.PUT, "$weeksUrl/${weekIds[1]}/complete", userToken)
                        )
                ).status
        )

        var found = false
        weekListLens(client(SecureRequest(Method.GET, weeksUrl, userToken))).map {
            if (it.id == weekIds[1]) {
                found = true
                assertTrue(it.complete)
            }
        }
        assertTrue(found)
    }

    @Test
    fun markWeekLocked() {

    }

    @Test
    fun markWeekReady() {
        assertEquals(
                expected = Status.OK,
                actual = client(
                        updateWeekReadyLens(
                                UpdateWeekReady(true),
                                SecureRequest(Method.PUT, "$weeksUrl/${weekIds[1]}/ready", userToken)
                        )
                ).status
        )

        var found = false
        val weeks = weekListLens(client(SecureRequest(Method.GET, weeksUrl, userToken)))
        weeks.map {
            if (it.id == weekIds[1]) {
                found = true
                assertTrue(it.ready)
            }
        }
        assertTrue(found)
    }

    @Test
    fun weekListSecured() {
        val actual = client(Request(Method.GET, weeksUrl))
        assertEquals(Status.UNAUTHORIZED, actual.status)
    }

}