package swami2020.game

import org.junit.AfterClass
import org.junit.BeforeClass
import swami2020.BaseTest
import swami2020.TestUtil
import swami2020.api.request.CreateGame
import swami2020.api.request.CreateSelection
import swami2020.exception.ItemNotFoundException
import swami2020.selection.NewUserSelection
import swami2020.selection.SelectionService
import java.util.*
import kotlin.test.*

class GameServiceTest : BaseTest() {

    lateinit var testId: UUID
    val week1 = UUID.fromString("7cbdd6bf-7545-4091-a227-4f29450632d7")

    val iowa = UUID.fromString("9579145e-1946-4f42-9c47-42fefb4eb8e6")
    val minnesota = UUID.fromString("fc623823-a492-4264-a960-8d5053d16f08")

    companion object {
        // Class under test
        lateinit var gameService: GameService
        lateinit var selectionService: SelectionService

        @JvmStatic
        @BeforeClass
        fun setUp() {
            gameService = TestUtil.appFactory.gameService
            selectionService = TestUtil.appFactory.selectionService
        }

        @JvmStatic
        @AfterClass
        fun clean() {
            selectionService.list().map {
                selectionService.delete(UUID.fromString(it.id))
            }
        }
    }

    @Test
    fun createAndDeleteGame() {
        val request = CreateGame(
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

        // Create user selections for this game and another

        val validSelection = selectionService.createSelection(
                NewUserSelection(
                        user = userId,
                        selection = actual.selections.first().id
                )
        )

        val invalidSelection = selectionService.createSelection(
                NewUserSelection(
                        user = UUID.randomUUID(),
                        selection = UUID.randomUUID()
                )
        )

        gameService.delete(testId)

        var validUserSelectionFound = true
        try {
            selectionService.fetch(validSelection)
        }catch(e: ItemNotFoundException) {
            validUserSelectionFound = false
        }

        assertFalse(validUserSelectionFound)

        var invalidUserSelectionFound = true
        try {
            selectionService.fetch(invalidSelection)
        }catch(e: ItemNotFoundException) {
            invalidUserSelectionFound = false
        }

        assertTrue(invalidUserSelectionFound)

        var found = true

        try {
            gameService.fetch(testId)
        } catch (e: ItemNotFoundException) {
            found = false
        }

        assertFalse(found)

    }

}