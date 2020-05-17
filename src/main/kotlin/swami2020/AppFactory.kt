package swami2020

import com.zaxxer.hikari.HikariDataSource
import swami2020.database.DatabaseConfig
import swami2020.properties.SwamiProperties
import swami2020.team.TeamRepository
import swami2020.team.TeamRoutes
import swami2020.team.TeamService

class AppFactory(properties: SwamiProperties) {
    val port = properties.serverProperties.port

    // Construct
    val databaseConfig = DatabaseConfig.build(properties.databaseProperties)
    val teamRepository = TeamRepository()
    val teamService = TeamService()
    val teamRoutes = TeamRoutes()

    //Set Up
    init {
        teamRepository.setUp(HikariDataSource(databaseConfig))
        teamService.setUp(teamRepository)
        teamRoutes.setUp(teamService)
    }
}