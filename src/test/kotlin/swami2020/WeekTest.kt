package swami2020

import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.format.Jackson.auto
import org.junit.BeforeClass
import swami2020.api.request.LoginRequest
import swami2020.api.response.Week
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class WeekTest: BaseTest() {

    companion object {
        val testId = UUID.fromString("7cbdd6bf-7545-4091-a227-4f29450632d7")
        private val weekListLens = Body.auto<Collection<Week>>().toLens()
        private val weekLens = Body.auto<Week>().toLens()

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
    }

// TODO: turn this back on when weeks are implemented

//    @Test
//    fun testListWeeks() {
//        val actual = client(SecureRequest(Method.GET, weeksUrl, token))
//        assertEquals(Status.OK, actual.status)
//
//        val weeks = weekListLens(actual)
//        assertEquals(5, weeks.size)
//
//        weeks.forEach {
//            assertNull(it.games)
//        }
//    }
//
//    @Test
//    fun testWeekListSecured() {
//        val actual = client(Request(Method.GET, weeksUrl))
//        assertEquals(Status.UNAUTHORIZED, actual.status)
//    }
//
//    @Test
//    fun testWeekFetchSecured() {
//        val actual = client(Request(Method.GET, "$weeksUrl/$testId"))
//        assertEquals(Status.UNAUTHORIZED, actual.status)
//    }
}