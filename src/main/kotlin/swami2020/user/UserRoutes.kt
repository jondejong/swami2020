package swami2020.user

import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.path
import swami2020.api.request.CreateUser
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import swami2020.api.response.User
import java.util.*

class UserRoutes : SwamiConfigurable {

    private lateinit var userService: UserService
    private val userLens = Body.auto<User>().toLens()
    private val userListLens = Body.auto<Collection<User>>().toLens()
    private val createUserLens = Body.auto<CreateUser>().toLens()

    override fun setUp(factory: AppFactory) {
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
        userLens(
                userService.fetch(UUID.fromString(
                        request.path("id")
                )),
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