package swami2020.user

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import swami2020.api.loginRequestLens
import swami2020.api.loginResponseLens
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable

class LoginRoutes : SwamiConfigurable {

    private lateinit var loginService: LoginService

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