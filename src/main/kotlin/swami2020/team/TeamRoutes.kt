package swami2020.team

import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.path
import swami2020.api.response.Team
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import java.util.*

class TeamRoutes : SwamiConfigurable {

    private lateinit var teamService: TeamService
    private val teamLens = Body.auto<Team>().toLens()
    private val teamListLens = Body.auto<Collection<Team>>().toLens()

    override fun setUp(factory: AppFactory) {
        this.teamService = factory.teamService
    }

    private val teamFetchHandler = { request: Request ->
        teamLens(
                teamService.fetch(UUID.fromString(
                        request.path("id")
                )),
                Response(Status.OK)
        )
    }

    private val teamListHandler = { _: Request ->
        teamListLens(teamService.list(), Response(Status.OK))
    }

    val routes = org.http4k.routing.routes(
            "/{id:.*}" bind Method.GET to teamFetchHandler,
            "/" bind Method.GET to teamListHandler
    )
}