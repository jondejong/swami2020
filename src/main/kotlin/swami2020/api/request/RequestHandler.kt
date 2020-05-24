package swami2020.api.request

import org.http4k.core.RequestContexts
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable

open class RequestHandler : SwamiConfigurable {
    lateinit var contexts: RequestContexts

    override fun setUp(factory: AppFactory) {
        this.contexts = factory.contexts
    }
}