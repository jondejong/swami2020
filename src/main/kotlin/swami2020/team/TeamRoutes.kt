package swami2020.team

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.path
import swami2020.api.teamLens
import swami2020.api.teamListLens
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import java.util.*

class TeamRoutes : SwamiConfigurable {

    private lateinit var teamService: TeamService

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