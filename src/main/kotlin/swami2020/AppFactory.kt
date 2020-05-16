package swami2020

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import swami2020.team.TeamRepository
import swami2020.team.TeamRoutes
import swami2020.team.TeamService
import java.util.*

/**
 * TODO: Replace with DI? (guice?)
 * TODO: Replace with properties loading
 */
class AppFactory(properties: SwamiProperties) {
    val port = Integer.parseInt(properties.server.getProperty("port"))

    // Construct
    val hikariConfig = HikariConfig(properties.database)
    val teamRepository = TeamRepository()
    val teamService = TeamService()
    val teamRoutes = TeamRoutes()

    //Set Up
    init {
        teamRepository.setUp(HikariDataSource(hikariConfig))
        teamService.setUp(teamRepository)
        teamRoutes.setUp(teamService)
    }
}