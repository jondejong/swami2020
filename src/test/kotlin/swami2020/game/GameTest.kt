package swami2020.game

import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.format.Jackson.auto
import org.junit.AfterClass
import org.junit.BeforeClass
import swami2020.BaseTest
import swami2020.SecureRequest
import swami2020.TestUtil
import swami2020.api.Game
import swami2020.api.request.*
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class GameTest : BaseTest() {

    companion object {

        val iowa = UUID.fromString("9579145e-1946-4f42-9c47-42fefb4eb8e6")
        val minnesota = UUID.fromString("fc623823-a492-4264-a960-8d5053d16f08")
        val nebraska = UUID.fromString("c7f2fdf8-b71e-48fe-b5fa-ac4c8c2cf993")
        val usc = UUID.fromString("2f400522-9e71-4cbf-964b-85d0b2899bc1")

        private val gameListLens = Body.auto<Collection<Game>>().toLens()
        private val gameLens = Body.auto<Game>().toLens()
        private val createGameLens = Body.auto<CreateGame>().toLens()
        private val completeGameLens = Body.auto<CompleteGame>().toLens()

        private val gamesUrl = "$urlBase/games"

        lateinit var expectedGame: Game
        lateinit var token: String

        // TODO: Create games for a week
        @BeforeClass
        @JvmStatic
        fun createGames() {
            authenticate()

            val gameService = TestUtil.appFactory.gameService
            val testGames = setOf(
                    CreateGame(
                            selections = setOf(
                                    CreateSelection(
                                            team = iowa,
                                            favorite = true,
                                            home = false
                                    ),
                                    CreateSelection(
                                            team = minnesota,
                                            favorite = false,
                                            home = true
                                    )
                            ),
                            complete = false,
                            cancelled = false,
                            week = weekIds[1]!!,
                            spread = 42F
                    ),
                    CreateGame(
                            selections = setOf(
                                    CreateSelection(
                                            team = nebraska,
                                            favorite = true,
                                            home = false
                                    ),
                                    CreateSelection(
                                            team = usc,
                                            favorite = false,
                                            home = true
                                    )
                            ),
                            complete = false,
                            cancelled = false,
                            week = weekIds[1]!!,
                            spread = 12F
                    ),
                    //Week 2
                    CreateGame(
                            selections = setOf(
                                    CreateSelection(
                                            team = iowa,
                                            favorite = true,
                                            home = false
                                    ),
                                    CreateSelection(
                                            team = usc,
                                            favorite = false,
                                            home = true
                                    )
                            ),
                            complete = false,
                            cancelled = false,
                            week = weekIds[2]!!,
                            spread = 27.5F
                    ),
                    CreateGame(
                            selections = setOf(
                                    CreateSelection(
                                            team = nebraska,
                                            favorite = true,
                                            home = false
                                    ),
                                    CreateSelection(
                                            team = minnesota,
                                            favorite = false,
                                            home = true
                                    )
                            ),
                            complete = false,
                            cancelled = false,
                            week = weekIds[2]!!,
                            spread = 24F
                    )
            )

            testGames.map { gameService.create(it) }
            expectedGame = TestUtil.appFactory.gameService.listByWeek(weekIds[1]!!).first()
        }

        private fun authenticate() {
            // Login
            token = loginResponseLens(
                    client(
                            loginRequestLens(
                                    Login(
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
            setOf<UUID>(weekIds[1]!!, weekIds[2]!!, weekIds[3]!!).map {
                TestUtil.appFactory.gameService.listByWeek(it).map {
                    TestUtil.appFactory.gameService.delete(it.id)
                }
            }
        }

        fun generateCompleteGameRequest(homeTeamScore: Int, visitingTeamScore: Int): CompleteGame {
            return CompleteGame(
                    expectedGame.selections.map {
                        if (it.home) SelectionScore(it.id, homeTeamScore) else SelectionScore(it.id, visitingTeamScore)
                    }
            )
        }
    }

    @Test
    fun listGamesByWeek() {
        val actual = client(SecureRequest(Method.GET, "$gamesUrl/week/${weekIds[2]}", token))
        assertEquals(Status.OK, actual.status)

        val games = gameListLens(actual)
        assertEquals(2, games.size)

        games.map {
            assertEquals(
                    expected = 2,
                    actual = it.week
            )
            assertEquals(
                    expected = weekIds[2]!!,
                    actual = it.weekId
            )
        }
    }

    @Test
    fun fetchGame() {
        val actual = client(SecureRequest(Method.GET, "$gamesUrl/${expectedGame.id}", token))
        assertEquals(Status.OK, actual.status)

        val actualGame = gameLens(actual)
        assertEquals(
                expected = expectedGame.id,
                actual = actualGame.id
        )
    }

    @Test
    fun list() {
        // Not needed long term
        val actual = client(SecureRequest(Method.GET, gamesUrl, token))
        assertEquals(Status.OK, actual.status)
    }

    @Test
    fun createAndDeleteGame() {
        val actual = client(SecureRequest(Method.GET, "$gamesUrl/week/${weekIds[3]}", token))
        assertEquals(Status.OK, actual.status)

        val games = gameListLens(actual)
        assertEquals(0, games.size)

        val request = CreateGame(
                selections = setOf(
                        CreateSelection(
                                team = iowa,
                                favorite = true,
                                home = false
                        ),
                        CreateSelection(
                                team = usc,
                                favorite = false,
                                home = true
                        )
                ),
                complete = false,
                cancelled = false,
                week = weekIds[3]!!,
                spread = 27.5F
        )

        val createResponse = client(
                createGameLens(
                        request,
                        SecureRequest(Method.POST, gamesUrl, token)
                )
        )

        assertEquals(Status.OK, createResponse.status)

        assertEquals(
                expected = 1,
                actual = gameListLens(
                        client(
                                SecureRequest(
                                        Method.GET,
                                        "$gamesUrl/week/${weekIds[3]}",
                                        token
                                )
                        )
                ).size
        )
    }

    @Test
    fun completeGame() {
        val homeTeamScore = 42
        val visitingTeamScore = 14
        val response = client(
                completeGameLens(
                        generateCompleteGameRequest(
                                homeTeamScore = homeTeamScore,
                                visitingTeamScore = visitingTeamScore
                        ),
                        SecureRequest(Method.PUT, "$gamesUrl/${expectedGame.id}/score", token)
                )
        )

        assertEquals(
                expected = Status.OK,
                actual = response.status
        )

        val actual = gameLens(
                client(
                        SecureRequest(Method.GET, "$gamesUrl/${expectedGame.id}", token)
                )
        )
        assertEquals(
                expected = true,
                actual = actual.complete
        )

        actual.selections.map {
            assertEquals(
                    expected = if (it.home) homeTeamScore else visitingTeamScore,
                    actual = it.score
            )
        }
    }

    @Test
    fun completeGameThatDoesNotExist() {
        val homeTeamScore = 42
        val visitingTeamScore = 14
        val response = client(
                completeGameLens(
                        generateCompleteGameRequest(
                                homeTeamScore = homeTeamScore,
                                visitingTeamScore = visitingTeamScore
                        ),
                        SecureRequest(Method.PUT, "$gamesUrl/${UUID.randomUUID()}/score", token)
                )
        )

        assertEquals(
                expected = Status.NOT_FOUND,
                actual = response.status
        )

    }


//    TODO: Implement this feature
//    @Test
//    fun cancelGame() {
//        assertTrue(false, "Implement me")
//    }

    @Test
    fun gameListSecured() {
        val actual = client(Request(Method.GET, gamesUrl))
        assertEquals(Status.UNAUTHORIZED, actual.status)
    }
}