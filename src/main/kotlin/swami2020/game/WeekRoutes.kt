package swami2020.game

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.path
import swami2020.api.*
import swami2020.api.request.RequestHandler
import swami2020.api.response.ID
import swami2020.api.response.Week
import swami2020.app.AppFactory
import java.util.*

class WeekRoutes : RequestHandler() {

    lateinit var weekService: WeekService

    override fun setUp(factory: AppFactory) {
        super.setUp(factory)
        this.weekService = factory.weekService
    }

    private val weekListHandler = { _: Request ->
        weekListLens(weekService.list().map {
            Week.from(it)
        }, Response(Status.OK))
    }

    private val currentWeekHandler = { _: Request ->
        weekLens(Week.from(weekService.fetchCurrent()), Response(Status.OK))
    }

    private val createWeekHandler = { request: Request ->
        idLens(
                ID(weekService.create(createWeekLens(request))),
                Response(Status.OK)
        )
    }

    private val updateCompleteHandler = { request: Request ->
        weekService.updateComplete(
                UUID.fromString(request.path("id")),
                updateWeekCompleteLens(request).complete
        )
        Response(Status.OK)
    }

    private val updateReadyHandler = { request: Request ->
        weekService.updateReady(
                UUID.fromString(request.path("id")),
                updateWeekReadyLens(request).ready
        )
        Response(Status.OK)
    }

    val routes = org.http4k.routing.routes(
            "/" bind Method.GET to weekListHandler,
            "/" bind Method.POST to createWeekHandler,
            "/current" bind Method.GET to currentWeekHandler,
            "/{id:.*}/ready" bind Method.PUT to updateReadyHandler,
            "/{id:.*}/complete" bind Method.PUT to updateCompleteHandler
    )
}