package swami2020.user

import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.bind
import swami2020.api.request.LoginRequest
import swami2020.api.response.LoginResponse
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable

class LoginRoutes : SwamiConfigurable {

    private lateinit var loginService: LoginService

    private val loginRequestLens = Body.auto<LoginRequest>().toLens()
    private val loginResponseLens = Body.auto<LoginResponse>().toLens()

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