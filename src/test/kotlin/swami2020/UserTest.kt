package swami2020

import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.format.Jackson.auto
import swami2020.api.request.CreateUser
import swami2020.api.response.User
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.*

class UserTest: BaseTest() {

    private val userLens = Body.auto<User>().toLens()
    private val userListLens = Body.auto<Collection<User>>().toLens()
    private val createUserLens = Body.auto<CreateUser>().toLens()
    private val usersPath = "users"
    private val usersUrl = "$urlBase/$usersPath"

    private val expectedUser = User(
            UUID.fromString("49c4db64-acd1-431f-b013-7f35895ec85b"),
            "Test",
            "User",
            "test.user@testemail.com"
    )

    @Test
    fun testUserUpdates() {
        val firstListResponse = client(Request(Method.GET, usersUrl))
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
                        Request(Method.POST, usersUrl)
                )
        )

        assertEquals(Status.OK, createResponse.status)

        val newUser = userLens(createResponse)
        assertNotNull(newUser.id)
        assertEquals("Jonny", newUser.firstName)
        assertEquals("Tester", newUser.lastName)
        assertEquals("jonny.tester@testemail.com", newUser.email)

        val postCreateListResponse = client(Request(Method.GET, usersUrl))
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

        val deleteResponse = client(Request(Method.DELETE, "$usersUrl/${newUser.id}"))
        assertEquals(Status.OK, deleteResponse.status)

        val finalListResponse = client(Request(Method.GET, usersUrl))
        assertEquals(Status.OK, finalListResponse.status)

        val finalUsers = userListLens(finalListResponse)
        assertEquals(2, finalUsers.size)

        users.forEach {
            assertNotEquals(it.id, newUser.id)
        }

    }

    @Test
    fun testUsersFetch() {
        val resp = client(Request(Method.GET, "$usersUrl/${expectedUser.id}"))
        assertEquals(Status.OK, resp.status)

        val actualUser = userLens(resp)
        assertEquals(expectedUser, actualUser)
    }

    @Test
    fun testUserNotFound() {
        val resp = client(Request(Method.GET, "$usersUrl/${UUID.randomUUID()}"))
        assertEquals(Status.NOT_FOUND, resp.status)
    }

    @Test
    fun testInvalidUserIdentifier() {
        val resp = client(Request(Method.GET, "$usersUrl/notUUID"))
        assertEquals(Status.BAD_REQUEST, resp.status)
    }
}