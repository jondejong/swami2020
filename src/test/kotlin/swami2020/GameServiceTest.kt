package swami2020

import swami2020.game.GameService
import org.junit.BeforeClass
import swami2020.api.request.CreateGameRequest
import swami2020.api.request.CreateSelectionRequest
import swami2020.exception.ItemNotFoundException
import java.util.*
import kotlin.test.*

class GameServiceTest : BaseTest() {

    lateinit var testId: UUID
    val week1 = UUID.fromString("7cbdd6bf-7545-4091-a227-4f29450632d7")

    val iowa = UUID.fromString("9579145e-1946-4f42-9c47-42fefb4eb8e6")
    val minnesota = UUID.fromString("fc623823-a492-4264-a960-8d5053d16f08")

    // TODO: Start here testing so we can use this for game endpoint testing

    companion object {
        // Class under test
        lateinit var gameService: GameService

        @JvmStatic
        @BeforeClass
        fun setUp() {
            gameService = TestUtil.appFactory.gameService
        }

    }

    @Test
    fun createAndDeleteGame() {
        val request = CreateGameRequest(
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
        )
        testId = gameService.create(request)

        val actual = gameService.fetch(testId)
        assertNotNull(actual)
        assertFalse(actual.complete)
        assertFalse(actual.cancelled)
        assertEquals(
                expected = 42F,
                actual = actual.spread
        )
        assertEquals(
                expected = 1,
                actual = actual.week
        )

        assertEquals(
                expected = 2,
                actual = actual.selections.size
        )

        actual.selections.map {
            if (it.team.id == iowa) {
                assertEquals(
                        expected = "Iowa",
                        actual = it.team.name
                )
                assertEquals(
                        expected = "Hawkeyes",
                        actual = it.team.nickName
                )
                assertEquals(
                        expected = iowa,
                        actual = it.team.id
                )
                assertEquals(
                        expected = "Big Ten",
                        actual = it.team.conference
                )
                assertTrue(it.favorite)
                assertFalse(it.home)
            } else {
                assertEquals(
                        expected = "Minnesota",
                        actual = it.team.name
                )
                assertEquals(
                        expected = "Golden Gophers",
                        actual = it.team.nickName
                )
                assertEquals(
                        expected = minnesota,
                        actual = it.team.id
                )
                assertFalse(it.favorite)
                assertTrue(it.home)
            }
        }

        gameService.delete(testId)

        var found = true

        try {
            gameService.fetch(testId)
        } catch (e: ItemNotFoundException) {
            found = false
        }

        assertFalse(found)

    }

    //TODO: Implement these when the features are developed

//    @Test
//    fun updateGameScore() {
//        gameService.updateGameScore()
//    }
//
//    @Test
//    fun cancelGame() {
//        gameService.cancelGame()
//    }

}