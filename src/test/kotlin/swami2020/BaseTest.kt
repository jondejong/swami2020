package swami2020

import org.http4k.client.OkHttp
import org.http4k.core.Body
import org.http4k.format.Jackson.auto
import org.junit.AfterClass
import org.junit.BeforeClass
import swami2020.api.response.ID
import swami2020.app.AppFactory
import java.util.*

open class BaseTest {
    companion object {

        val weekIds = mapOf<Int, UUID>(
                1 to UUID.fromString("7cbdd6bf-7545-4091-a227-4f29450632d7"),
                2 to UUID.fromString("3d581ea2-b3c8-4576-87df-683eb4abd8ee"),
                3 to UUID.fromString("232a5c59-8a23-4917-a4cf-7f0d367cb4ad")
        )

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

        val loginRequestLens = Body.auto<swami2020.api.request.Login>().toLens()
        val loginResponseLens = Body.auto<swami2020.api.response.Login>().toLens()
        val client = OkHttp()


        val server = "http://localhost"
        val urlBase = "$server:${TestUtil.port}"

        val loginUrl = "$urlBase/$loginPath"
        val usersUrl = "$urlBase/$usersPath"

        val idLens = Body.auto<ID>().toLens()
    }

    val authenticationHeader = AppFactory.AUTHENTICATION_HEADER
}