package swami2020

import org.http4k.client.OkHttp
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.auto
import org.junit.AfterClass
import org.junit.BeforeClass
import swami2020.api.request.Login
import swami2020.api.response.ID
import swami2020.app.AppFactory
import java.util.*

open class BaseTest {
    companion object {

        lateinit var userToken: String
        lateinit var adminToken: String

        val usersPath = "users"
        val teamsPath = "teams"
        val loginPath = "login"
        val weeksPath = "weeks"
        val selectionsPath = "selections"

        val userId = UUID.fromString("49c4db64-acd1-431f-b013-7f35895ec85b")
        val adminId = UUID.fromString("f438f2ee-28ae-482f-b6a6-8c7474510092")

        val loginRequestLens = Body.auto<swami2020.api.request.Login>().toLens()
        val loginResponseLens = Body.auto<swami2020.api.response.Login>().toLens()
        val client = OkHttp()

        val server = "http://localhost"
        val urlBase = "$server:${TestUtil.port}"

        val loginUrl = "$urlBase/$loginPath"
        val usersUrl = "$urlBase/$usersPath"
        val teamsUrl = "$urlBase/$teamsPath"
        val weeksUrl = "$urlBase/$weeksPath"
        val selectionsUrl = "$urlBase/$selectionsPath"

        val idLens = Body.auto<ID>().toLens()

        val weekIds = mapOf<Int, UUID>(
                1 to UUID.fromString("7cbdd6bf-7545-4091-a227-4f29450632d7"),
                2 to UUID.fromString("3d581ea2-b3c8-4576-87df-683eb4abd8ee"),
                3 to UUID.fromString("232a5c59-8a23-4917-a4cf-7f0d367cb4ad")
        )

        private fun authenticate() {
            // Login
            userToken = loginResponseLens(
                    client(
                            loginRequestLens(
                                    Login(
                                            username = "test.user@testemail.com",
                                            password = "P@ssw0rd1"
                                    ),
                                    Request(Method.POST, loginUrl)
                            )
                    )
            ).token

            adminToken = loginResponseLens(
                    client(
                            loginRequestLens(
                                    Login(
                                            username = "test.admin@testemail.com",
                                            password = "P@ssw0rd1"
                                    ),
                                    Request(Method.POST, loginUrl)
                            )
                    )
            ).token
        }

        // Application under test
        @BeforeClass
        @JvmStatic
        fun setup() {
            TestUtil.start()
            authenticate()
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            TestUtil.stop()
        }

    }

    val authenticationHeader = AppFactory.AUTHENTICATION_HEADER
}