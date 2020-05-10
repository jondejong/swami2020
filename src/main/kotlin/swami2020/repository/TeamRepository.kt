package swami2020.repository

import com.jondejong.eswami.model.tables.Team.TEAM
import org.jooq.DSLContext
import org.jooq.impl.DSL
import swami2020.dto.Team
import swami2020.exception.ItemNotFoundException
import java.util.*

class TeamRepository {

//    var context : DSLContext

    private val context : DSLContext = DSL.using(
            "jdbc:postgresql://localhost:5432/swami",
            "swami_user",
            "Password1"
    )

    companion object Builder {
        fun build() : TeamRepository {
            return TeamRepository()
        }
    }

    fun list() : Collection<Team> {
        context.use { context ->
            val teams = context.select(TEAM.ID, TEAM.NAME, TEAM.NICK_NAME)
                    .from(TEAM)
                    .fetchInto(Team::class.java)
            return teams
        }
    }

    fun fetch(id: UUID) : Team {
        context.use { context ->
             val teams = context.select(TEAM.ID, TEAM.NAME, TEAM.NICK_NAME)
                    .from(TEAM)
                    .where(TEAM.ID.eq(id.toString()))
                    .fetchInto(Team::class.java)

            if(teams?.size != 1) {
                throw ItemNotFoundException()
            }
            return teams[0]
        }
    }
}