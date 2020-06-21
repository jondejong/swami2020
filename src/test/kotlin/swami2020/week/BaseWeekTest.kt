package swami2020.week

import org.junit.AfterClass
import swami2020.BaseTest
import swami2020.TestUtil
import swami2020.selection.SelectionTest
import java.util.*

open class BaseWeekTest : BaseTest() {

    companion object {
        private val service = TestUtil.appFactory.weekService

        val setReadyCurrentWeek = { week: Int ->
            // The "current" week is ready but not completed
            resetWeeks()
            for (number in 1.until(week)) {
                service.updateReady(
                        id = weekIds[number]!!,
                        status = true
                )
                service.updateComplete(
                        id = weekIds[number]!!,
                        status = true
                )
            }
            service.updateReady(
                    id = weekIds[week]!!,
                    status = true
            )
        }

        val setCompletedCurrentWeek = { week: Int ->
            // The "current" week is ready but not completed
            resetWeeks()
            for (number in 1.until(week + 1)) {
                service.updateReady(
                        id = weekIds[number]!!,
                        status = true
                )
                service.updateComplete(
                        id = weekIds[number]!!,
                        status = true
                )
            }
        }

        val setLockedCurrentWeek = { week: Int ->
            resetWeeks()
            for (number in 1.until(week)) {
                service.updateReady(
                        id = weekIds[number]!!,
                        status = true
                )
                service.updateLocked(
                        id = weekIds[number]!!,
                        status = true
                )
                service.updateComplete(
                        id = weekIds[number]!!,
                        status = true
                )
            }
            service.updateReady(
                    id = weekIds[week]!!,
                    status = true
            )
            service.updateLocked(
                    id = weekIds[week]!!,
                    status = true
            )
            service.updateComplete(
                    id = weekIds[week]!!,
                    status = false
            )
        }

        @AfterClass
        @JvmStatic
        fun resetWeeks() {
            SelectionTest.weekService.list().map {
                if (it.number > 5) {
                    service.delete(UUID.fromString(it.id))
                } else {
                    SelectionTest.weekService.updateLocked(
                            id = UUID.fromString(it.id),
                            status = false
                    )

                    SelectionTest.weekService.updateReady(
                            id = UUID.fromString(it.id),
                            status = false
                    )

                    SelectionTest.weekService.updateComplete(
                            id = UUID.fromString(it.id),
                            status = false
                    )
                }
            }
        }

    }
}