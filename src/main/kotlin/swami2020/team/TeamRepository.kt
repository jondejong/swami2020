package swami2020.team

import com.jondejong.swami.model.tables.Conference.CONFERENCE
import com.jondejong.swami.model.tables.Team.TEAM
import org.jooq.DSLContext
import swami2020.api.response.Team
import swami2020.app.SwamiRepository
import swami2020.exception.ItemNotFoundException
import java.util.*

class TeamRepository() : SwamiRepository() {

    private val teamMapper = { context: DSLContext ->
        context.select(TEAM.ID, TEAM.NAME, TEAM.NICK_NAME, CONFERENCE.NAME.`as`("conference"))
                .from(TEAM)
                .join(CONFERENCE).on(CONFERENCE.ID.eq(TEAM.CONFERENCE))
    }

    fun list(): Collection<Team> {
        context.use { context ->
            return teamMapper(context)
                    .fetchInto(Team::class.java)
        }
    }

    fun fetch(id: UUID): Team {
        context.use { context ->
            val teams = teamMapper(context)
                    .where(TEAM.ID.eq(id.toString()))
                    .fetchInto(Team::class.java)

            if (teams?.size != 1) {
                throw ItemNotFoundException()
            }
            return teams[0]
        }
    }
}