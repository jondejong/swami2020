/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package swami2020

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import swami2020.app.AppFactory
import swami2020.filters.ErrorHandlerFilter
import swami2020.properties.DatabaseProperties
import swami2020.properties.ServerProperties
import swami2020.properties.SwamiProperties

class App(appFactory: AppFactory) {

    private val port = appFactory.port

    private val handlers = routes(
            "/hello" bind routes(
                    "/{name:.*}" bind Method.GET to { request: Request -> Response(OK).body("Hello, ${request.path("name")}!") }
            ),
            "login" bind appFactory.loginRoutes.routes,
            "teams" bind appFactory.teamRoutes.routes,
            "users" bind appFactory.userRoutes.routes.withFilter(appFactory.authenticationFilter.authenticationFilter),
            "games" bind appFactory.gameRoutes.routes.withFilter(appFactory.authenticationFilter.authenticationFilter)
    )

    // Compose all handlers
    private val app =
            ServerFilters.InitialiseRequestContext(appFactory.contexts)
                    .then(ErrorHandlerFilter.errorFilter)
                    .then(handlers)

    private val jettyServer = app.asServer(Jetty(port))

    fun start() {
        jettyServer.start()
        println("Server started. Listening on port $port")
    }

    fun stop() {
        jettyServer.stop()
    }
}

fun main(args: Array<String>) {
    //TODO: Load properties from YAML
    val swamiProperties = SwamiProperties(
            ServerProperties(9000),
            DatabaseProperties(
                    "jdbc:postgresql://localhost:5432/swami",
                    "swami_user",
                    "Password_1"
            )
    )

    val appFactory = AppFactory(swamiProperties)
    val app = App(appFactory)
    app.start()
}
