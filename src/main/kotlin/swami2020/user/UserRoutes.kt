package swami2020.user

import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import org.http4k.routing.path
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import swami2020.response.User
import java.util.*

class UserRoutes : SwamiConfigurable {

    private lateinit var userService: UserService
    private val userLens = Body.auto<User>().toLens()
    private val userListLens = Body.auto<Collection<User>>().toLens()

    override fun setUp(factory: AppFactory) {
        this.userService = factory.userService
    }

    private val userFetchHandler = { request: Request ->
        userLens(
                userService.fetch(UUID.fromString(
                        request.path("id")
                )),
                Response(Status.OK)
        )
    }

    private val userListHandler = { _: Request ->
        userListLens(userService.list(), Response(Status.OK))
    }

    val routes = org.http4k.routing.routes(
            "/{id:.*}" bind Method.GET to userFetchHandler,
            "/" bind Method.GET to userListHandler
    )
}