package swami2020.week

import org.junit.AfterClass
import swami2020.BaseTest
import swami2020.TestUtil
import java.util.*

open class BaseWeekTest : BaseTest() {

    companion object {

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