package swami2020.selection

import org.http4k.core.Method
import org.http4k.core.Status
import org.junit.AfterClass
import org.junit.BeforeClass
import swami2020.SecureRequest
import swami2020.TestUtil
import swami2020.api.makeSelectionLens
import swami2020.api.request.CreateGame
import swami2020.api.request.CreateSelection
import swami2020.api.request.MakeSelection
import swami2020.api.request.UpdateUserWeekSubmitted
import swami2020.api.updateUserWeekSubmittedLens
import swami2020.api.userWeekSelectionsLens
import swami2020.week.BaseWeekTest
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SelectionTest : BaseWeekTest() {

    companion object {

        val iowa = UUID.fromString("9579145e-1946-4f42-9c47-42fefb4eb8e6")
        val minnesota = UUID.fromString("fc623823-a492-4264-a960-8d5053d16f08")

        val nebraska = UUID.fromString("c7f2fdf8-b71e-48fe-b5fa-ac4c8c2cf993")
        val usc = UUID.fromString("2f400522-9e71-4cbf-964b-85d0b2899bc1")

        val arizona = UUID.fromString("8773d7fd-3091-4555-be6c-e7eddeecab37")
        val stanford = UUID.fromString("79016ef7-7aef-4b8a-b3d8-e649c28f5d58")

        val kansas = UUID.fromString("e007ffb2-0123-40db-bf35-deb729e4e12b")
        val wisconsin = UUID.fromString("e4a93a91-a8ee-433d-a300-5180929d9387")

        val gameService = TestUtil.appFactory.gameService
        val weekService = TestUtil.appFactory.weekService
        val selectionService = TestUtil.appFactory.selectionService

        lateinit var selectionIds: List<UUID>


        @JvmStatic
        @BeforeClass
        fun createSelections() {
            // TODO: Create 4 games in week 1
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
                            week = weekIds[1]!!,
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
                            week = weekIds[1]!!,
                            spread = 12F
                    )
            )

            testGames.map { gameService.create(it) }

            // Get List of selection ids, one per game
            selectionIds = gameService.list().map { it.selections.first().id }.toList()

            // Create 2 selections for our test user
            for (i in 0..1) {
                selectionService.createSelection(
                        NewUserSelection(
                                user = userId,
                                selection = selectionIds[i]
                        )
                )
            }

            // Create 1 other
            selectionService.createSelection(
                    NewUserSelection(
                            user = adminId,
                            selection = selectionIds[2]
                    )
            )
        }

        fun toggleWeekSubmit(number: Int, submitted: Boolean) {
            TestUtil.appFactory.userWeekService.updateSubmitted(weekIds[number]!!, userId, submitted)
        }

        fun deleteGames() {
            TestUtil.appFactory.gameService.list().map {
                TestUtil.appFactory.gameService.delete(it.id)
            }
        }

        fun deleteUserWeeks() {
            TestUtil.appFactory.userWeekService.list().map {
                TestUtil.appFactory.userWeekService.delete(UUID.fromString(it.id))
            }
        }

        @JvmStatic
        @AfterClass
        fun cleanUp() {
            deleteGames()
            deleteUserWeeks()
            toggleWeekSubmit(1, false)
        }
    }

    @Test
    fun userCanViewSelections() {
        val response = client(SecureRequest(Method.GET, "$selectionsUrl/1", userToken))
        assertEquals(
                expected = Status.OK,
                actual = response.status
        )

        val userWeek = userWeekSelectionsLens(response)

        assertEquals(
                expected = 2,
                actual = userWeek.userSelections.size
        )

        assertEquals(
                expected = 1,
                actual = userWeek.week
        )

    }

    @Test
    fun userMakesSelection() {
        val expectedSelection = selectionIds[2]
        val response = client(
                makeSelectionLens(
                        MakeSelection(expectedSelection),
                        SecureRequest(Method.POST, "$selectionsUrl/1", userToken)
                )
        )

        assertEquals(
                expected = Status.OK,
                actual = response.status
        )

        val selectionId = idLens(response).id

        val userWeek = userWeekSelectionsLens(client(SecureRequest(Method.GET, "$selectionsUrl/1", userToken)))

        assertEquals(
                expected = 3,
                actual = userWeek.userSelections.size
        )

        assertEquals(
                expected = false,
                actual = userWeek.submitted
        )

        var found = false
        userWeek.userSelections.map {
            if (it.id == selectionId && it.selection.id == expectedSelection) {
                found = true
            }
        }
        assertTrue(found)
    }

    @Test
    fun userCannotSeeOtherUsersForReadyWeek() {
        resetWeeks()
        setReadyCurrentWeek(1)
        assertEquals(
                expected = Status.UNAUTHORIZED,
                actual = client(
                        SecureRequest(Method.GET, "$selectionsUrl/1?user=${adminId}", userToken)
                ).status
        )
    }

    @Test
    fun userCanSeeOtherUsersForLockedWeek() {
        resetWeeks()
        setLockedCurrentWeek(1)
        val response = client(
                SecureRequest(Method.GET, "$selectionsUrl/1?user=${adminId}", userToken)
        )
        assertEquals(
                expected = Status.OK,
                actual = response.status
        )

        val selections = userWeekSelectionsLens(response)

        assertEquals(
                expected = adminId,
                actual = selections.userId
        )

        assertEquals(
                expected = 1,
                actual = selections.week
        )

        assertEquals(
                expected = 1,
                actual = selections.userSelections.size
        )

    }

    @Test
    fun userCanSubmitPicksForReadyWeek() {
        resetWeeks()
        deleteUserWeeks()
        setReadyCurrentWeek(1)

        assertEquals(
                expected = false,
                actual = userWeekSelectionsLens(client(SecureRequest(Method.GET, "$selectionsUrl/1", userToken))).submitted
        )

        val response = client(
                updateUserWeekSubmittedLens(
                        UpdateUserWeekSubmitted(true),
                        SecureRequest(Method.PUT, "$selectionsUrl/1/submitted", userToken)
                )
        )

        assertEquals(
                expected = Status.OK,
                actual = response.status
        )

        assertEquals(
                expected = true,
                actual = userWeekSelectionsLens(client(SecureRequest(Method.GET, "$selectionsUrl/1", userToken))).submitted
        )

    }

    @Test
    fun userCannotSubmitPicksForNotReadyWeek() {
        resetWeeks()
        deleteUserWeeks()
        setLockedCurrentWeek(1)
        assertEquals(
                expected = Status.BAD_REQUEST,
                actual = client(
                        updateUserWeekSubmittedLens(
                                UpdateUserWeekSubmitted(true),
                                SecureRequest(Method.PUT, "$selectionsUrl/2/submitted", userToken)
                        )
                ).status
        )
    }

    @Test
    fun userCannotSubmitPicksForLockedWeek() {
        resetWeeks()
        deleteUserWeeks()
        setLockedCurrentWeek(1)

        assertEquals(
                expected = Status.BAD_REQUEST,
                actual = client(
                        updateUserWeekSubmittedLens(
                                UpdateUserWeekSubmitted(true),
                                SecureRequest(Method.PUT, "$selectionsUrl/1/submitted", userToken)
                        )
                ).status
        )
    }

    @Test
    fun userCanDeleteSelection() {
        val expectedSelection = selectionIds[3]
        val selectionId = idLens(client(
                makeSelectionLens(
                        MakeSelection(expectedSelection),
                        SecureRequest(Method.POST, "$selectionsUrl/1", userToken)
                )
        )).id

        val userSelections = userWeekSelectionsLens(
                client(SecureRequest(Method.GET, "$selectionsUrl/1", userToken))
        ).userSelections

        val expectedSelectionCount = userSelections.size - 1

        val response = client(SecureRequest(Method.DELETE, "$selectionsUrl/1/$selectionId", userToken))
        assertEquals(
                expected = Status.OK,
                actual = response.status
        )

        assertEquals(
                expected = expectedSelectionCount,
                actual = userWeekSelectionsLens(
                        client(SecureRequest(Method.GET, "$selectionsUrl/1", userToken))
                ).userSelections.size
        )
    }

    @Test
    fun userCannotDeleteSelectionForSubmittedWeek() {
        resetWeeks()
        setReadyCurrentWeek(1)
        toggleWeekSubmit(1, true)
        val response = client(SecureRequest(Method.DELETE, "$selectionsUrl/1/${selectionIds.first()}", userToken))
        assertEquals(
                expected = Status.BAD_REQUEST,
                actual = response.status
        )
    }
}