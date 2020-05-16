package swami2020.team

import com.jondejong.eswami.model.tables.Team.TEAM
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import swami2020.exception.ItemNotFoundException
import swami2020.response.Team
import java.util.*
import javax.sql.DataSource

class TeamRepository() {

     private lateinit var context : DSLContext

    fun setUp(dataSource: DataSource) {
        context = DSL.using(dataSource, SQLDialect.POSTGRES)
    }

    fun list() : Collection<Team> {
        context.use { context ->
            return context.select(TEAM.ID, TEAM.NAME, TEAM.NICK_NAME)
                    .from(TEAM)
                    .fetchInto(Team::class.java)
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