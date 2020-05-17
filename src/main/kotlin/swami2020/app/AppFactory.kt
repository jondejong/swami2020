package swami2020.app

import com.zaxxer.hikari.HikariDataSource
import swami2020.database.DatabaseConfig
import swami2020.properties.SwamiProperties
import swami2020.team.TeamRepository
import swami2020.team.TeamRoutes
import swami2020.team.TeamService
import swami2020.user.UserRepository
import swami2020.user.UserRoutes
import swami2020.user.UserService

class AppFactory(properties: SwamiProperties) {
    val port = properties.serverProperties.port

    // Construct
    private val databaseConfig = DatabaseConfig.build(properties.databaseProperties)

    // Teams
    val teamRepository = TeamRepository()
    val teamService = TeamService()
    val teamRoutes = TeamRoutes()

    //Users
    val userRepository = UserRepository()
    val userService = UserService()
    var userRoutes = UserRoutes()

    val dataSource = HikariDataSource(databaseConfig)

    //Set Up
    init {

        // Team
        teamRepository.setUp(this)
        teamService.setUp(this)
        teamRoutes.setUp(this)

        //User
        userRepository.setUp(this)
        userService.setUp(this)
        userRoutes.setUp(this)
    }
}