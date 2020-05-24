package swami2020.app

import com.zaxxer.hikari.HikariDataSource
import org.http4k.core.RequestContexts
import swami2020.database.DatabaseConfig
import swami2020.filters.AuthenticationFilter
import swami2020.properties.SwamiProperties
import swami2020.team.TeamRepository
import swami2020.team.TeamRoutes
import swami2020.team.TeamService
import swami2020.user.*

class AppFactory(properties: SwamiProperties) {
    companion object {
        const val AUTHENTICATION_HEADER = "X-AUTH-TOKEN"
        const val AUTHENTICATED_USER = "AUTHENTICATED-USER"
        const val IS_ADMIN = "IS-ADMIN"
    }

    val port = properties.serverProperties.port

    // HandleRequests
    val contexts = RequestContexts()

    // Construct
    private val databaseConfig = DatabaseConfig.build(properties.databaseProperties)

    // Teams
    val teamRepository = TeamRepository()
    val teamService = TeamService()
    val teamRoutes = TeamRoutes()

    // Users
    val userRepository = UserRepository()
    val userService = UserService()
    var userRoutes = UserRoutes()

    // Login
    val loginService = LoginService()
    val loginRoutes = LoginRoutes()
    val authenticationFilter = AuthenticationFilter()

    val dataSource = HikariDataSource(databaseConfig)

    //Set Up
    init {

        // Team
        teamRepository.setUp(this)
        teamService.setUp(this)
        teamRoutes.setUp(this)

        // User
        userRepository.setUp(this)
        userService.setUp(this)
        userRoutes.setUp(this)

        // Login
        loginService.setUp(this)
        loginRoutes.setUp(this)
        authenticationFilter.setUp(this)
    }
}