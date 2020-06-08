package swami2020.user

import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable

class LoginRoutes : SwamiConfigurable {

    private lateinit var loginService: LoginService

    private val loginRequestLens = Body.auto<swami2020.api.request.Login>().toLens()
    private val loginResponseLens = Body.auto<swami2020.api.response.Login>().toLens()

    override fun setUp(factory: AppFactory) {
        this.loginService = factory.loginService
    }

    private val loginHandler = { request: Request ->
        val loginRequest = loginRequestLens(request)
        loginResponseLens(
                loginService.validateUser(
                        username = loginRequest.username,
                        password = loginRequest.password
                ),
                Response(Status.OK)
        )
    }

    val routes = org.http4k.routing.routes(
            "/" bind Method.POST to loginHandler
    )

}