package swami2020.service

import swami2020.dto.Team
import swami2020.repository.TeamRepository
import java.util.*

class TeamService : Service() {

    private val teamRepository = TeamRepository.Builder.build()

    fun fetch(id: UUID) : Team {
        return executeLoad(id, teamRepository::fetch, Team.Builder, id)
    }
}