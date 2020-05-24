package swami2020

import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.format.Jackson.auto
import org.junit.AfterClass
import org.junit.BeforeClass
import swami2020.api.request.LoginRequest
import swami2020.api.Game
import swami2020.api.request.CreateGameRequest
import swami2020.api.request.CreateSelectionRequest
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameTest : BaseTest() {

    companion object {

        val week1 = UUID.fromString("7cbdd6bf-7545-4091-a227-4f29450632d7")
        val week2 = UUID.fromString("3d581ea2-b3c8-4576-87df-683eb4abd8ee")
        val week3 = UUID.fromString("232a5c59-8a23-4917-a4cf-7f0d367cb4ad")

        val iowa = UUID.fromString("9579145e-1946-4f42-9c47-42fefb4eb8e6")
        val minnesota = UUID.fromString("fc623823-a492-4264-a960-8d5053d16f08")
        val nebraska = UUID.fromString("c7f2fdf8-b71e-48fe-b5fa-ac4c8c2cf993")
        val usc = UUID.fromString("2f400522-9e71-4cbf-964b-85d0b2899bc1")

        private val gameListLens = Body.auto<Collection<Game>>().toLens()
        private val gameLens = Body.auto<Game>().toLens()

        private val gamesUrl = "$urlBase/games"

        lateinit var token: String

        val gameIds = mutableSetOf<UUID>()

        // TODO: Create games for a week
        @BeforeClass
        @JvmStatic
        fun createGames() {
            authenticate()

            val gameService = TestUtil.appFactory.gameService
            val testGames = setOf(
                    CreateGameRequest(
                            selections = setOf(
                                    CreateSelectionRequest(
                                            team = iowa,
                                            favorite = true,
                                            home = false
                                    ),
                                    CreateSelectionRequest(
                                            team = minnesota,
                                            favorite = false,
                                            home = true
                                    )
                            ),
                            complete = false,
                            cancelled = false,
                            week = week1,
                            spread = 42F
                    ),
                    CreateGameRequest(
                            selections = setOf(
                                    CreateSelectionRequest(
                                            team = nebraska,
                                            favorite = true,
                                            home = false
                                    ),
                                    CreateSelectionRequest(
                                            team = usc,
                                            favorite = false,
                                            home = true
                                    )
                            ),
                            complete = false,
                            cancelled = false,
                            week = week1,
                            spread = 12F
                    ),
                    //Week 2
                    CreateGameRequest(
                            selections = setOf(
                                    CreateSelectionRequest(
                                            team = iowa,
                                            favorite = true,
                                            home = false
                                    ),
                                    CreateSelectionRequest(
                                            team = usc,
                                            favorite = false,
                                            home = true
                                    )
                            ),
                            complete = false,
                            cancelled = false,
                            week = week2,
                            spread = 27.5F
                    ),
                    CreateGameRequest(
                            selections = setOf(
                                    CreateSelectionRequest(
                                            team = nebraska,
                                            favorite = true,
                                            home = false
                                    ),
                                    CreateSelectionRequest(
                                            team = minnesota,
                                            favorite = false,
                                            home = true
                                    )
                            ),
                            complete = false,
                            cancelled = false,
                            week = week2,
                            spread = 24F
                    )
            )

            testGames.map {
                gameIds.add(gameService.create(it))
            }
        }

        fun authenticate() {
            // Login
            token = loginResponseLens(
                    client(
                            loginRequestLens(
                                    LoginRequest(
                                            username = "test.user@testemail.com",
                                            password = "P@ssw0rd1"
                                    ),
                                    Request(Method.POST, loginUrl)
                            )
                    )
            ).token
        }

        @AfterClass
        @JvmStatic
        fun destroy() {
            //TODO: Destroy the games that were created for the tests
            gameIds.map { TestUtil.appFactory.gameService.delete(it) }
        }

    }

    @Test
    fun listGamesByWeek() {
        val actual = client(SecureRequest(Method.GET, "$gamesUrl/week/${week2}", token))
        assertEquals(Status.OK, actual.status)

        val games = gameListLens(actual)
        assertEquals(2, games.size)

        games.map {
            assertEquals(
                    expected = 2,
                    actual = it.week
            )
            assertEquals(
                    expected = week2,
                    actual = it.weekId
            )
        }
    }

    @Test
    fun fetchGame() {
        val id = gameIds.first()
        val actual = client(SecureRequest(Method.GET, "$gamesUrl/${id}", token))
        assertEquals(Status.OK, actual.status)

        val actualGame = gameLens(actual)
        assertEquals(
                expected = id,
                actual = actualGame.id
        )
    }

    @Test
    fun listAndDeleteGames() {
        val actual = client(SecureRequest(Method.GET, gamesUrl, token))
        assertEquals(Status.OK, actual.status)

        assertEquals(4, gameListLens(actual).size)

        SecureRequest(Method.DELETE, "$gamesUrl/${gameIds.first()}", token)

        val postDeleteActual = client(SecureRequest(Method.GET, gamesUrl, token))
        assertEquals(Status.OK, actual.status)

        assertEquals(4, gameListLens(postDeleteActual).size)
    }

//    TODO: implement these as the features are developed

//    @Test
//    fun addScore() {
//        assertTrue(false, "Implement me")
//    }
//
//    @Test
//    fun updateScore() {
//        assertTrue(false, "Implement me")
//    }
//
//    @Test
//    fun cancelGame() {
//        assertTrue(false, "Implement me")
//    }

    @Test
    fun testGameSecured() {
        val actual = client(Request(Method.GET, gamesUrl))
        assertEquals(Status.UNAUTHORIZED, actual.status)
    }
}