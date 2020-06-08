package swami2020

import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.format.Jackson.auto
import swami2020.api.request.Login
import swami2020.api.response.User
import java.util.*
import kotlin.test.*

class LoginTest : BaseTest() {

    private val userLens = Body.auto<User>().toLens()

    private val expectedUser = User(
            id = UUID.fromString("49c4db64-acd1-431f-b013-7f35895ec85b"),
            firstName = "Test",
            lastName = "User",
            email = "test.user@testemail.com"
    )

    @Test
    fun login() {
        val loginRequest = Login(
                username = expectedUser.email,
                password = "P@ssw0rd1"
        )

        val response = client(
                loginRequestLens(
                        loginRequest,
                        Request(Method.POST, loginUrl)
                )
        )

        assertEquals(Status.OK, response.status)

        val loginResponse = loginResponseLens(response)
        assertEquals(loginResponse.user, expectedUser)

        val selfResponse = client(
                Request(
                        Method.GET,
                        "$usersUrl/self"
                ).header(
                        authenticationHeader,
                        loginResponse.token
                )
        )
        assertEquals(Status.OK, selfResponse.status)
        assertEquals(
                expectedUser,
                userLens(selfResponse)
        )
    }

    @Test
    fun authentication() {
        // Hitting a secured endpoint should fail
        val unauthenticatedActual = client(Request(Method.GET, "$usersUrl"))
        assertEquals(Status.UNAUTHORIZED, unauthenticatedActual.status)

        // Login
        val response = loginResponseLens(
                client(
                        loginRequestLens(
                                Login(
                                        username = expectedUser.email,
                                        password = "P@ssw0rd1"
                                ),
                                Request(Method.POST, loginUrl)
                        )
                )
        )

        // Hitting a secured endpoint should pass
        val authenticatedActual = client(
                Request(
                        Method.GET,
                        "$usersUrl"
                ).header(authenticationHeader, response.token))

        assertEquals(Status.OK, authenticatedActual.status)
    }

    @Test
    fun badPassword() {
        val loginRequest = Login(
                username = expectedUser.email,
                password = "thisisabadpassword"
        )

        val response = client(
                loginRequestLens(
                        loginRequest,
                        Request(Method.POST, loginUrl)
                )
        )

        assertEquals(Status.UNAUTHORIZED, response.status)
    }
}