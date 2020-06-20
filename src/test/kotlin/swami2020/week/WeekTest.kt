package swami2020.week

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import swami2020.SecureRequest
import swami2020.api.*
import swami2020.api.request.CreateWeek
import swami2020.api.request.UpdateWeekComplete
import swami2020.api.request.UpdateWeekLocked
import swami2020.api.request.UpdateWeekReady
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
        val i = weekIds[1]
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
        assertEquals(
                expected = Status.OK,
                actual = client(
                        updateWeekLockedLens(
                                UpdateWeekLocked(true),
                                SecureRequest(Method.PUT, "$weeksUrl/${weekIds[1]}/locked", userToken)
                        )
                ).status
        )

        var found = false
        weekListLens(client(SecureRequest(Method.GET, weeksUrl, userToken))).map {
            if (it.id == weekIds[1]) {
                found = true
                assertTrue(it.locked)
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