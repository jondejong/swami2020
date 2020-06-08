package swami2020

import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.format.Jackson.auto
import org.junit.AfterClass
import org.junit.BeforeClass
import swami2020.api.request.CreateWeek
import swami2020.api.request.LoginRequest
import swami2020.api.request.UpdateWeekComplete
import swami2020.api.request.UpdateWeekReady
import swami2020.api.response.Week
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class WeekTest : BaseTest() {

    private val week1 = UUID.fromString("7cbdd6bf-7545-4091-a227-4f29450632d7")

    companion object {

        private val updateWeekCompleteLens = Body.auto<UpdateWeekComplete>().toLens()
        private val updateWeekReadyLens = Body.auto<UpdateWeekReady>().toLens()
        private val createWeekLens = Body.auto<CreateWeek>().toLens()
        private val weekListLens = Body.auto<Collection<Week>>().toLens()

        private val weeksUrl = "$urlBase/weeks"

        lateinit var token: String

        @BeforeClass
        @JvmStatic
        fun authenticate() {
            // Login
            token = loginResponseLens(
                    client(
                            loginRequestLens(
                                    LoginRequest(
                                            username = "test.user@testemail.com",
                                            password = "P@ssw0rd1"
                                    ),
                                    Request(Method.POST, loginUrl)
                            )
                    )
            ).token
        }

        @AfterClass
        @JvmStatic
        fun cleanUp() {
            //Clean up data modified by the test
            TestUtil.appFactory.weekService.list().map {

                // Delete the week we created
                if (it.number == 6) {
                    TestUtil.appFactory.weekService.delete(UUID.fromString(it.id))
                } else {
                    TestUtil.appFactory.weekService.updateComplete(UUID.fromString(it.id), false)
                    TestUtil.appFactory.weekService.updateReady(UUID.fromString(it.id), false)
                }
            }
        }
    }


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
                                SecureRequest(Method.PUT, "$weeksUrl/${week1}/complete", token)
                        )
                ).status
        )

        var found = false
        weekListLens(client(SecureRequest(Method.GET, weeksUrl, token))).map {
            if (it.id == week1) {
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
                                SecureRequest(Method.PUT, "$weeksUrl/${week1}/ready", token)
                        )
                ).status
        )

        var found = false
        val weeks = weekListLens(client(SecureRequest(Method.GET, weeksUrl, token)))
        weeks.map {
            if (it.id == week1) {
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