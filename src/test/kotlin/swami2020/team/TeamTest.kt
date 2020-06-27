/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package swami2020.team

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import swami2020.BaseTest
import swami2020.api.response.Team
import swami2020.api.teamLens
import swami2020.api.teamListLens
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TeamTest : BaseTest() {

    private val expectedTeam = Team(
            UUID.fromString("9579145e-1946-4f42-9c47-42fefb4eb8e6"),
            "Iowa",
            "Hawkeyes",
            "Big Ten"
    )

    @Test
    fun teamList() {
        val resp = client(Request(Method.GET, teamsUrl))
        assertNotNull(resp)
        assertEquals(Status.OK, resp.status)

        val teams = teamListLens(resp)

        assertEquals(50, teams.size)
        teams.forEach { team ->
            assertNotNull(team.id)
            assertNotNull(team.name)
            assertNotNull(team.nickName)
            assertNotNull(team.conference)
        }
    }

    @Test
    fun teamFetch() {
        val resp = client(Request(Method.GET, "$teamsUrl/${expectedTeam.id}"))
        assertEquals(Status.OK, resp.status)

        val actualTeam = teamLens(resp)
        assertEquals(expectedTeam, actualTeam)
    }

    @Test
    fun teamNotFound() {
        val resp = client(Request(Method.GET, "$teamsUrl/${UUID.randomUUID()}"))
        assertEquals(Status.NOT_FOUND, resp.status)
    }

    @Test
    fun invalidTeamIdentifier() {
        val resp = client(Request(Method.GET, "$teamsUrl/notUUID"))
        assertEquals(Status.BAD_REQUEST, resp.status)
    }

}
