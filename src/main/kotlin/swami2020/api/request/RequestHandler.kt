package swami2020.api.request

import org.http4k.core.Body
import org.http4k.core.RequestContexts
import org.http4k.format.Jackson.auto
import swami2020.api.response.ID
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable

open class RequestHandler : SwamiConfigurable {
    lateinit var contexts: RequestContexts

    val idLens = Body.auto<ID>().toLens()

    override fun setUp(factory: AppFactory) {
        this.contexts = factory.contexts
    }
}