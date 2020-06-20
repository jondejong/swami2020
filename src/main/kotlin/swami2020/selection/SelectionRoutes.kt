package swami2020.selection

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.path
import swami2020.api.request.RequestHandler
import swami2020.api.userWeekSelectionsLens
import swami2020.app.AppFactory
import swami2020.user.AuthenticatedUser

class SelectionRoutes : RequestHandler() {

    private lateinit var selectionService: SelectionService

    override fun setUp(factory: AppFactory) {
        super.setUp(factory)
        this.selectionService = factory.selectionService
    }

    private val selectionListHandler = { request: Request ->
        val user: AuthenticatedUser? = contexts[request][AppFactory.AUTHENTICATED_USER]
        userWeekSelectionsLens(
                selectionService.listUserWeek(
                        week = request.path("week")!!.toInt(),
                        user = user!!.id
                ),
                Response(Status.OK)
        )
    }

    val routes = org.http4k.routing.routes(
            "/{week:.*}" bind Method.GET to selectionListHandler
    )
}