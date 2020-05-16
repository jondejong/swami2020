package swami2020.dto

import com.jondejong.eswami.model.tables.Conference
import java.util.*

data class Team(val id: UUID, val name: String, val nickName: String) {

    object Builder : DefaultableBuilder<Team>{
        override fun default(): Team {
            return default(UUID.randomUUID())
        }

        override fun default(id: UUID): Team {
            return Team(id, "", "")
        }
    }
}