package swami2020.week

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import swami2020.SecureRequest
import swami2020.api.request.CreateWeek
import swami2020.api.request.UpdateWeekComplete
import swami2020.api.request.UpdateWeekReady
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WeekTest : BaseWeekTest() {

    @Test
    fun createAndListWeeks() {
        val actual = client(SecureRequest(Method.GET, weeksUrl, token))
        assertEquals(Status.OK, actual.status)

        val weeks = weekListLens(actual)
        assertEquals(5, weeks.size)

        assertEquals(
                expected = Status.OK,
                actual = client(
                        createWeekLens(
                                CreateWeek(6),
                                SecureRequest(Method.POST, weeksUrl, token)
                        )
                ).status
        )

        assertEquals(
                expected = 6,
                actual = weekListLens(
                        client(SecureRequest(Method.GET, weeksUrl, token))
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
                                SecureRequest(Method.PUT, "$weeksUrl/${ids[1]}/complete", token)
                        )
                ).status
        )

        var found = false
        weekListLens(client(SecureRequest(Method.GET, weeksUrl, token))).map {
            if (it.id == ids[1]) {
                found = true
                assertTrue(it.complete)
            }
        }
        assertTrue(found)
    }

    @Test
    fun markWeekReady() {
        assertEquals(
                expected = Status.OK,
                actual = client(
                        updateWeekReadyLens(
                                UpdateWeekReady(true),
                                SecureRequest(Method.PUT, "$weeksUrl/${ids[1]}/ready", token)
                        )
                ).status
        )

        var found = false
        val weeks = weekListLens(client(SecureRequest(Method.GET, weeksUrl, token)))
        weeks.map {
            if (it.id == ids[1]) {
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