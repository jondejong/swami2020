package swami2020.team

import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.path
import swami2020.response.Team
import java.util.*

class TeamRoutes {

    private lateinit var teamService: TeamService
    private val teamLens = Body.auto<Team>().toLens()
    private val teamListLens = Body.auto<Collection<Team>>().toLens()

    fun setUp(teamService: TeamService) {
        this.teamService = teamService
    }

    private val teamFetchHandler = { request: Request ->
        val id = request.path("id")
        val team = teamService.fetch(UUID.fromString(id))
        teamLens(team, Response(Status.OK))
    }

    private val teamListHandler = { _: Request ->
        teamListLens(teamService.list(), Response(Status.OK))
    }

    val routes = org.http4k.routing.routes(
                "/{id:.*}" bind Method.GET to teamFetchHandler,
                "/" bind Method.GET to teamListHandler
        )
}