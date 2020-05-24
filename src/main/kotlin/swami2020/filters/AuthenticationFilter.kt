package swami2020.filters

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.RequestContexts
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import swami2020.exception.ItemNotFoundException
import swami2020.exception.UnauthenticatedException
import swami2020.user.UserService

class AuthenticationFilter : SwamiConfigurable {

    lateinit var userService: UserService
    lateinit var contexts: RequestContexts

    override fun setUp(factory: AppFactory) {
        this.userService = factory.userService
        this.contexts = factory.contexts
    }

    val authenticationFilter = Filter { next: HttpHandler ->
        { request: Request ->
            val token = request.header(AppFactory.AUTHENTICATION_HEADER)
            if (token == null) {
                throw UnauthenticatedException()
            } else {
                try {
                    val user = userService.fetchByToken(token)
                    contexts[request][AppFactory.AUTHENTICATED_USER] = user
                } catch (itemNotFoundException: ItemNotFoundException) {
                    throw UnauthenticatedException()
                }
            }
            next(request)
        }
    }
}