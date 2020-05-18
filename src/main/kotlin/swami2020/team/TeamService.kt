package swami2020.team

import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import swami2020.api.response.Team
import java.util.*

class TeamService() : SwamiConfigurable {

    private lateinit var teamRepository: TeamRepository

    override fun setUp(factory: AppFactory) {
        this.teamRepository = factory.teamRepository
    }

    fun list(): Collection<Team> {
        return teamRepository.list()
    }

    fun fetch(id: UUID): Team {
        return teamRepository.fetch(id)
    }
}