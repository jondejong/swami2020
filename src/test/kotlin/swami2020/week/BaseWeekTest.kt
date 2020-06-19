package swami2020.week

import org.http4k.core.Body
import org.http4k.format.Jackson.auto
import org.junit.AfterClass
import swami2020.BaseTest
import swami2020.TestUtil
import swami2020.api.request.CreateWeek
import swami2020.api.request.UpdateWeekComplete
import swami2020.api.request.UpdateWeekReady
import swami2020.api.response.Week
import java.util.*

open class BaseWeekTest : BaseTest() {

    companion object {

        val updateWeekCompleteLens = Body.auto<UpdateWeekComplete>().toLens()
        val updateWeekReadyLens = Body.auto<UpdateWeekReady>().toLens()
        val createWeekLens = Body.auto<CreateWeek>().toLens()
        val weekListLens = Body.auto<Collection<Week>>().toLens()
        val weekLens = Body.auto<Week>().toLens()

        val weeksUrl = "${BaseTest.urlBase}/weeks"

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