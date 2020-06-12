package swami2020.week

import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.auto
import org.junit.AfterClass
import org.junit.BeforeClass
import swami2020.BaseTest
import swami2020.TestUtil
import swami2020.api.request.CreateWeek
import swami2020.api.request.Login
import swami2020.api.request.UpdateWeekComplete
import swami2020.api.request.UpdateWeekReady
import swami2020.api.response.Week
import java.util.*

open class BaseWeekTest : BaseTest() {

    companion object {

        val ids = mapOf<Int, UUID>(
                1 to UUID.fromString("7cbdd6bf-7545-4091-a227-4f29450632d7"),
                2 to UUID.fromString("3d581ea2-b3c8-4576-87df-683eb4abd8ee"),
                3 to UUID.fromString("232a5c59-8a23-4917-a4cf-7f0d367cb4ad")
        )

        val updateWeekCompleteLens = Body.auto<UpdateWeekComplete>().toLens()
        val updateWeekReadyLens = Body.auto<UpdateWeekReady>().toLens()
        val createWeekLens = Body.auto<CreateWeek>().toLens()
        val weekListLens = Body.auto<Collection<Week>>().toLens()
        val weekLens = Body.auto<Week>().toLens()

        val weeksUrl = "${BaseTest.urlBase}/weeks"

        lateinit var token: String

        @BeforeClass
        @JvmStatic
        fun authenticate() {
            // Login
            token = BaseTest.loginResponseLens(
                    BaseTest.client(
                            BaseTest.loginRequestLens(
                                    Login(
                                            username = "test.user@testemail.com",
                                            password = "P@ssw0rd1"
                                    ),
                                    Request(Method.POST, BaseTest.loginUrl)
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
}