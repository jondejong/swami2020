/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package swami2020

import org.http4k.core.*
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import swami2020.exception.ItemNotFoundException
import swami2020.filters.ErrorHandlerFilter
import java.util.*

class App(appFactory: AppFactory) {
    private val port = appFactory.port
    private val teamRoutes = appFactory.teamRoutes

    // Compose the routes
    private val handlers = routes(
            "/hello" bind routes(
                    "/{name:.*}" bind Method.GET to { request: Request -> Response(OK).body("Hello, ${request.path("name")}!") }
            ),
            "teams" bind teamRoutes.routes
    )

    // Compose all handlers
    private val app =
            ErrorHandlerFilter.errorFilter
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
    // Database
    val databaseProperties = Properties()
    databaseProperties.setProperty("jdbcUrl", "jdbc:postgresql://localhost:5432/swami")
    databaseProperties.setProperty("username", "swami_user")
    databaseProperties.setProperty("password", "Password_1")

    // Server
    val serverProperties = Properties()
    serverProperties.setProperty("port", "9000")

    val swamiProperties = SwamiProperties()
    swamiProperties.database = databaseProperties
    swamiProperties.server = serverProperties

    val appFactory = AppFactory(swamiProperties)
    val app = App(appFactory)
    app.start()
}
