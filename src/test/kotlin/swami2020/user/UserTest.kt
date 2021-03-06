package swami2020.user

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import swami2020.BaseTest
import swami2020.SecureRequest
import swami2020.api.createUserLens
import swami2020.api.request.CreateUser
import swami2020.api.response.User
import swami2020.api.userLens
import swami2020.api.userListLens
import java.util.*
import kotlin.test.*

class UserTest : BaseTest() {

    companion object {

        val expectedUser = User(
                UUID.fromString("49c4db64-acd1-431f-b013-7f35895ec85b"),
                "Test",
                "User",
                "test.user@testemail.com"
        )

    }

    @Test
    fun userUpdates() {
        val firstListResponse = client(SecureRequest(Method.GET, usersUrl, userToken))
        assertEquals(Status.OK, firstListResponse.status)

        val users = userListLens(firstListResponse)

        assertEquals(2, users.size)
        users.forEach { user ->
            assertNotNull(user.email)
            assertNotNull(user.firstName)
            assertNotNull(user.lastName)
            assertNotNull(user.id)
        }

        val createUser = CreateUser(
                "Jonny",
                "Tester",
                "jonny.tester@testemail.com",
                "pAssword-22"
        )

        val createResponse = client(
                createUserLens(
                        createUser,
                        SecureRequest(Method.POST, usersUrl, userToken)
                )
        )

        assertEquals(Status.OK, createResponse.status)

        val newUser = userLens(createResponse)
        assertNotNull(newUser.id)
        assertEquals("Jonny", newUser.firstName)
        assertEquals("Tester", newUser.lastName)
        assertEquals("jonny.tester@testemail.com", newUser.email)

        val postCreateListResponse = client(SecureRequest(Method.GET, usersUrl, userToken))
        assertEquals(Status.OK, postCreateListResponse.status)
        val postCreateUsers = userListLens(postCreateListResponse)

        assertEquals(3, postCreateUsers.size)
        var found = false
        postCreateUsers.forEach { user ->
            assertNotNull(user.email)
            assertNotNull(user.firstName)
            assertNotNull(user.lastName)
            assertNotNull(user.id)

            if (
                    user.firstName == "Jonny" &&
                    user.lastName == "Tester" &&
                    user.email == "jonny.tester@testemail.com"
            ) {
                found = true
            }
        }

        assertTrue(found, "Newly created user not found")

        val deleteResponse = client(SecureRequest(Method.DELETE, "$usersUrl/${newUser.id}", userToken))
        assertEquals(Status.OK, deleteResponse.status)

        val finalListResponse = client(SecureRequest(Method.GET, usersUrl, userToken))
        assertEquals(Status.OK, finalListResponse.status)

        val finalUsers = userListLens(finalListResponse)
        assertEquals(2, finalUsers.size)

        users.forEach {
            assertNotEquals(it.id, newUser.id)
        }
    }

    @Test
    fun fetchUsers() {
        val resp = client(SecureRequest(Method.GET, "$usersUrl/${expectedUser.id}", userToken))
        assertEquals(Status.OK, resp.status)

        val actualUser = userLens(resp)
        assertEquals(expectedUser, actualUser)
    }

    @Test
    fun userNotFound() {
        val resp = client(SecureRequest(Method.GET, "$usersUrl/${UUID.randomUUID()}", userToken))
        assertEquals(Status.NOT_FOUND, resp.status)
    }

    @Test
    fun userListSecured() {
        val resp = client(Request(Method.GET, "$usersUrl"))
        assertEquals(Status.UNAUTHORIZED, resp.status)
    }

    @Test
    fun userFetchSecured() {
        val resp = client(Request(Method.GET, "$usersUrl/${UUID.randomUUID()}"))
        assertEquals(Status.UNAUTHORIZED, resp.status)
    }
}