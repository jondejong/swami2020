package swami2020.team

import swami2020.response.Team
import java.util.*

class TeamService() {

    private lateinit var teamRepository : TeamRepository

    fun setUp(teamRepository: TeamRepository) {
        this.teamRepository = teamRepository
    }

    fun list() : Collection<Team> {
        return teamRepository.list()
    }

    fun fetch(id: UUID) : Team {
        return teamRepository.fetch(id)
    }
}