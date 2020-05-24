package swami2020

import org.http4k.client.OkHttp
import org.http4k.core.Body
import org.http4k.format.Jackson.auto
import org.junit.AfterClass
import org.junit.BeforeClass
import swami2020.api.request.LoginRequest
import swami2020.api.response.LoginResponse
import swami2020.app.AppFactory

open class BaseTest {
    companion object {

        // Application under test
        @BeforeClass
        @JvmStatic
        fun setup() {
            TestUtil.start()
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            TestUtil.stop()
        }

        val usersPath = "users"
        val teamsPath = "teams"
        val loginPath = "login"

        val loginRequestLens = Body.auto<LoginRequest>().toLens()
        val loginResponseLens = Body.auto<LoginResponse>().toLens()
        val client = OkHttp()


        val server = "http://localhost"
        val urlBase = "$server:${TestUtil.port}"

        val loginUrl = "$urlBase/$loginPath"
        val usersUrl = "$urlBase/$usersPath"
    }

    val authenticationHeader = AppFactory.AUTHENTICATION_HEADER
}