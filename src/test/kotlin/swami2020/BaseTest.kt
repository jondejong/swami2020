package swami2020

import org.http4k.client.OkHttp
import org.junit.AfterClass
import org.junit.BeforeClass

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
    }

    private val server = "http://localhost"

    val client = OkHttp()
    val urlBase = "$server:${TestUtil.port}"

}