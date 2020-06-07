package swami2020.game

import com.jondejong.swami.model.tables.Conference.CONFERENCE
import com.jondejong.swami.model.tables.Game.GAME
import com.jondejong.swami.model.tables.Selection.SELECTION
import com.jondejong.swami.model.tables.Team.TEAM
import com.jondejong.swami.model.tables.Week.WEEK
import org.jooq.DSLContext
import swami2020.app.SwamiRepository
import swami2020.exception.ItemNotFoundException

class GameRepository : SwamiRepository() {

    private val gameContextMapper = { context: DSLContext ->
        context.select(
                GAME.ID,
                GAME.CANCELLED,
                GAME.COMPLETE,
                GAME.SPREAD,
                WEEK.NUMBER.`as`("week"),
                WEEK.ID.`as`("weekId"),
                SELECTION.ID.`as`("selectionId"),
                SELECTION.HOME.`as`("selectionHome"),
                SELECTION.FAVORITE.`as`("selectionFavorite"),
                TEAM.ID.`as`("selectionTeamId"),
                TEAM.NAME.`as`("selectionTeamName"),
                TEAM.NICK_NAME.`as`("selectionTeamNickName"),
                CONFERENCE.NAME.`as`("selectionConference")
        )
                .from(GAME)
                .join(WEEK).on(WEEK.ID.eq(GAME.WEEK))
                .join(SELECTION).on(SELECTION.GAME.eq(GAME.ID))
                .join(TEAM).on(SELECTION.TEAM.eq(TEAM.ID))
                .join(CONFERENCE).on(TEAM.CONFERENCE.eq(CONFERENCE.ID))
    }

    fun list(): Collection<GameRecord> {
        context.use { context ->
            try {
                return gameContextMapper(context)
                        .fetchInto(GameRecord::class.java)
            } catch (e: Exception) {
                println("${e.message}")
                throw e
            }
        }
    }

    fun listByWeek(id: String): Collection<GameRecord> {
        context.use { context ->
            try {
                return gameContextMapper(context)
                        .where(WEEK.ID.eq(id.toString()))
                        .fetchInto(GameRecord::class.java)
            } catch (e: Exception) {
                println("${e.message}")
                throw e
            }
        }
    }

    fun fetch(id: String): Collection<GameRecord> {
        context.use { context ->
            val gamesRecords = gameContextMapper(context).where(GAME.ID.eq(id))
                    .fetchInto((GameRecord::class.java))

            if (gamesRecords?.size != 2) {
                throw ItemNotFoundException()
            }
            return gamesRecords
        }
    }

    fun create(game: NewGame) {
        context.use { context ->
            context.insertInto(
                    GAME,
                    GAME.ID,
                    GAME.CANCELLED,
                    GAME.COMPLETE,
                    GAME.WEEK,
                    GAME.SPREAD
            ).values(
                    game.id.toString(),
                    game.cancelled,
                    game.complete,
                    game.week.toString(),
                    game.spread
            ).execute()
        }
    }

    fun delete(id: String) {
        context.use { context ->
            context.deleteFrom(GAME)
                    .where(GAME.ID.eq(id))
                    .execute()
        }
    }
}