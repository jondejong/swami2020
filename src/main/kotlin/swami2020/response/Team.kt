package swami2020.response

import java.util.*

data class Team(val id: UUID, val name: String, val nickName: String, val conference: String) {

    object ResponseBuilder : DefaultableResponseBuilder<Team> {
        override fun default(): Team {
            return default(UUID.randomUUID())
        }

        override fun default(id: UUID): Team {
            return Team(id, "", "", "")
        }
    }
}