package swami2020.repository

import swami2020.dto.Team
import java.util.*

class TeamRepository {

    companion object Builder {
        fun build() : TeamRepository {
            return TeamRepository()
        }
    }

    public fun fetch(id: UUID) : Team {
        return Team(id, "Nebraska", "Huskers")
    }

}