package swami2020.week

import org.junit.Test
import swami2020.TestUtil
import java.util.*
import kotlin.test.assertEquals

class WeekServiceTest : BaseWeekTest() {

    @Test
    fun fetchByNumber() {
        assertEquals(
                expected = ids[1],
                actual = UUID.fromString(
                        TestUtil.appFactory.weekService.fetchByNumber(1).id
                )
        )
        assertEquals(
                expected = ids[2],
                actual = UUID.fromString(
                        TestUtil.appFactory.weekService.fetchByNumber(2).id
                )
        )
        assertEquals(
                expected = ids[3],
                actual = UUID.fromString(
                        TestUtil.appFactory.weekService.fetchByNumber(3).id
                )
        )
    }
}