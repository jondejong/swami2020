package swami2020.ui

import org.junit.AfterClass
import org.junit.BeforeClass
import swami2020.BaseTest
import swami2020.TestUtil
import swami2020.api.request.CreateGame
import swami2020.api.request.CreateSelection
import java.util.*
import kotlin.test.Test

/**
 * This whole class is a hack to with rapid UI devlopment. Needs to be removed from the main test suite.
 * But for now it's fast and needs to clean up after itself, so it's harmless.
 */
class UITestData {

    companion object {

        val iowa = UUID.fromString("9579145e-1946-4f42-9c47-42fefb4eb8e6")
        val minnesota = UUID.fromString("fc623823-a492-4264-a960-8d5053d16f08")

        val nebraska = UUID.fromString("c7f2fdf8-b71e-48fe-b5fa-ac4c8c2cf993")
        val usc = UUID.fromString("2f400522-9e71-4cbf-964b-85d0b2899bc1")

        val arizona = UUID.fromString("8773d7fd-3091-4555-be6c-e7eddeecab37")
        val stanford = UUID.fromString("79016ef7-7aef-4b8a-b3d8-e649c28f5d58")

        val kansas = UUID.fromString("e007ffb2-0123-40db-bf35-deb729e4e12b")
        val wisconsin = UUID.fromString("e4a93a91-a8ee-433d-a300-5180929d9387")

        val weekService = TestUtil.appFactory.weekService
        val gameService = TestUtil.appFactory.gameService

        @JvmStatic
        @BeforeClass
        fun setUpData() {
            startWeek()
            createGames()
        }

        @AfterClass
        @JvmStatic
        fun cleanUp() {
            cleanUpGames()
            resetWeeks()
        }

        fun startWeek() {
            weekService.updateReady(
                    id = UUID.fromString("7cbdd6bf-7545-4091-a227-4f29450632d7"),
                    status = true
            )
        }

        fun createGames() {
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
                            week = BaseTest.weekIds[1]!!,
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
                            week = BaseTest.weekIds[1]!!,
                            spread = 12F
                    ),
                    CreateGame(
                            selections = setOf(
                                    CreateSelection(
                                            team = arizona,
                                            favorite = true,
                                            home = false
                                    ),
                                    CreateSelection(
                                            team = stanford,
                                            favorite = false,
                                            home = true
                                    )
                            ),
                            complete = false,
                            cancelled = false,
                            week = BaseTest.weekIds[1]!!,
                            spread = 12F
                    ),
                    CreateGame(
                            selections = setOf(
                                    CreateSelection(
                                            team = kansas,
                                            favorite = true,
                                            home = false
                                    ),
                                    CreateSelection(
                                            team = wisconsin,
                                            favorite = false,
                                            home = true
                                    )
                            ),
                            complete = false,
                            cancelled = false,
                            week = BaseTest.weekIds[1]!!,
                            spread = 12F
                    )
            )

            testGames.map { gameService.create(it) }

        }

        fun cleanUpGames() {
            gameService.list().map { gameService.delete(it.id) }
        }

        fun resetWeeks() {
            weekService.list().map {
                if (it.number > 5) {
                    weekService.delete(UUID.fromString(it.id))
                } else {
                    weekService.updateLocked(
                            id = UUID.fromString(it.id),
                            status = false
                    )

                    weekService.updateReady(
                            id = UUID.fromString(it.id),
                            status = false
                    )

                    weekService.updateComplete(
                            id = UUID.fromString(it.id),
                            status = false
                    )
                }
            }
        }
    }

    @Test
    fun testTheUI() {
        println("Put a breakpoint here ")
    }
}

