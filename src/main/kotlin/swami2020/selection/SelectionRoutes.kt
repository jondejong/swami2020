package swami2020.selection

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.path
import swami2020.api.idLens
import swami2020.api.makeSelectionLens
import swami2020.api.request.RequestHandler
import swami2020.api.response.ID
import swami2020.api.updateUserWeekSubmittedLens
import swami2020.api.userWeekSelectionsLens
import swami2020.app.AppFactory
import swami2020.exception.IllegalDataStateException
import swami2020.game.WeekService
import swami2020.user.AuthenticatedUser
import java.util.*

class SelectionRoutes : RequestHandler() {

    private lateinit var selectionService: SelectionService
    private lateinit var userWeekService: UserWeekService
    private lateinit var weekService: WeekService

    override fun setUp(factory: AppFactory) {
        super.setUp(factory)
        this.selectionService = factory.selectionService
        this.userWeekService = factory.userWeekService
        this.weekService = factory.weekService
    }

    private val userWeekRequest = { request: Request ->
        val user: AuthenticatedUser? = contexts[request][AppFactory.AUTHENTICATED_USER]
        val queryUser = request.query("user")
        UserWeekRequest(
                loggedInUser = user!!.id,
                queryUser = if (queryUser != null) UUID.fromString(queryUser) else user.id,
                week = request.path("week")!!.toInt()
        )
    }

    private val selectionListHandler = { request: Request ->
        userWeekSelectionsLens(
                selectionService.listUserWeek(userWeekRequest(request)),
                Response(Status.OK)
        )
    }

    private val makeSelectionHandler = { request: Request ->
        val userWeek = userWeekRequest(request)

        val id = selectionService.createSelection(
                NewUserSelection(
                        user = userWeek.queryUser,
                        selection = makeSelectionLens(request).selection
                )
        )

        idLens(ID(id), Response(Status.OK))
    }

    private val submitWeekHandler = { request: Request ->
        val userWeek = userWeekRequest(request)
        userWeekService.updateSubmitted(
                user = userWeek.loggedInUser,
                week = UUID.fromString(weekService.fetchByNumber(userWeek.week).id),
                submitted = updateUserWeekSubmittedLens(request).submitted
        )
        Response(Status.OK)
    }

    private val deleteSelectionHandler = { request: Request ->
        val userWeek = userWeekRequest(request)
        val weekId = UUID.fromString(weekService.fetchByNumber(userWeek.week).id)
        if (userWeekService.isSubmitted(
                        week = weekId,
                        user = userWeek.loggedInUser
                )) {
            throw IllegalDataStateException()
        }
        val selection = UUID.fromString(request.path("selection"))
        selectionService.delete(selection)
        idLens(ID(selection), Response(Status.OK))
    }

    val routes = org.http4k.routing.routes(
            "/{week:.*}" bind Method.GET to selectionListHandler,
            "/{week:.*}" bind Method.POST to makeSelectionHandler,
            "/{week:.*}/submitted" bind Method.PUT to submitWeekHandler,
            "/{week:.*}/{selection:.*}" bind Method.DELETE to deleteSelectionHandler
    )
}