package swami2020.user

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.path
import swami2020.api.createUserLens
import swami2020.api.request.RequestHandler
import swami2020.api.userLens
import swami2020.api.userListLens
import swami2020.app.AppFactory
import java.util.*

class UserRoutes : RequestHandler() {

    private lateinit var userService: UserService

    override fun setUp(factory: AppFactory) {
        super.setUp(factory)
        this.userService = factory.userService
    }

    private val createUserHandler = { request: Request ->
        val createUser = createUserLens(request)
        userLens(
                userService.create(createUser),
                Response(Status.OK)
        )
    }

    private val userFetchHandler = { request: Request ->
        var id = request.path("id")

        //TODO: Make this a different route
        if (id == "self") {
            val user: AuthenticatedUser? = contexts[request][AppFactory.AUTHENTICATED_USER]
            id = user?.id.toString()
        }

        // Special case for fetching the logged in user
        userLens(
                userService.fetch(UUID.fromString(id)),
                Response(Status.OK)
        )
    }

    private val deleteUserHandler = { request: Request ->
        userService.delete(UUID.fromString(request.path("id")))
        Response(Status.OK)
    }

    private val userListHandler = { _: Request ->
        userListLens(userService.list(), Response(Status.OK))
    }

    val routes = org.http4k.routing.routes(
            "/{id:.*}" bind Method.GET to userFetchHandler,
            "/{id:.*}" bind Method.DELETE to deleteUserHandler,
            "/" bind Method.GET to userListHandler,
            "/" bind Method.POST to createUserHandler
    )
}