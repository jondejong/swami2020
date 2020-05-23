package swami2020

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import kotlin.test.assertEquals
import kotlin.test.*

class AppTest: BaseTest()  {

    // Health
    @Test
    fun testHealth() {
        val resp = client(Request(Method.GET, "$urlBase/hello/jonny"))
        assertNotNull(resp)
        assertEquals(Status.OK, resp.status)
        assertEquals("Hello, jonny!", resp.bodyString())
    }

    // Errors
    @Test
    fun testBadURLNotFound() {
        val resp = client(Request(Method.GET, "$urlBase/things"))
        assertEquals(Status.NOT_FOUND, resp.status)
    }
}