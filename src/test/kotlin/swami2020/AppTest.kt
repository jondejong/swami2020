package swami2020

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AppTest : BaseTest() {

    // Health
    @Test
    fun health() {
        val resp = client(Request(Method.GET, "$urlBase/hello/jonny"))
        assertNotNull(resp)
        assertEquals(Status.OK, resp.status)
        assertEquals("Hello, jonny!", resp.bodyString())
    }

    // Errors
    @Test
    fun badURLNotFound() {
        val resp = client(Request(Method.GET, "$urlBase/things"))
        assertEquals(Status.NOT_FOUND, resp.status)
    }
}