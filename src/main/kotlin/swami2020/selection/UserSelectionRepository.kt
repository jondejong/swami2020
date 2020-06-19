package swami2020.selection

import com.jondejong.swami.model.Tables.*
import com.jondejong.swami.model.tables.pojos.UserSelection
import org.jooq.DSLContext
import swami2020.app.SwamiRepository

class UserSelectionRepository : SwamiRepository() {

    var selectionMapper = { context: DSLContext ->
        context.select(
                USER_SELECTION.ID,
                USER_SELECTION.SELECTION,
                USER_SELECTION.SWAMI_USER
        ).from(USER_SELECTION)
    }

    fun listByUserWeek(user: String, week: Int): Collection<UserSelectionRecord> {
        context.use { context ->
            return context.select(
                    USER_SELECTION.ID,
                    SELECTION.ID.`as`("selectionId"),
                    SELECTION.HOME,
                    SELECTION.FAVORITE,
                    SELECTION.SCORE,
                    TEAM.ID.`as`("teamId"),
                    TEAM.NAME.`as`("teamName"),
                    TEAM.NICK_NAME.`as`("teamNickName"),
                    CONFERENCE.NAME.`as`("conference")
            ).from(USER_SELECTION)
                    .join(SELECTION).on(SELECTION.ID.eq(USER_SELECTION.SELECTION))
                    .join(TEAM).on(TEAM.ID.eq(SELECTION.TEAM))
                    .join(CONFERENCE).on(CONFERENCE.ID.eq(TEAM.CONFERENCE))
                    .join(GAME).on(GAME.ID.eq(SELECTION.GAME))
                    .join(WEEK).on(WEEK.ID.eq(GAME.WEEK))
                    .join(SWAMI_USER).on(SWAMI_USER.ID.eq(USER_SELECTION.SWAMI_USER))
                    .where(WEEK.NUMBER.eq(week))
                    .and(SWAMI_USER.ID.eq(user))
                    .fetchInto(UserSelectionRecord::class.java)
        }
    }

    fun fetch(id: String): Collection<UserSelection> {
        context.use { context ->
            return selectionMapper(context)
                    .where(USER_SELECTION.ID.eq(id))
                    .fetchInto(UserSelection::class.java)
        }
    }

    fun delete(id: String) {
        context.use { context ->
            context.deleteFrom(USER_SELECTION)
                    .where(USER_SELECTION.ID.eq(id))
                    .execute()
        }
    }

    fun deleteBySelection(id: String) {
        context.use { context ->
            context.deleteFrom(USER_SELECTION)
                    .where(USER_SELECTION.SELECTION.eq(id))
                    .execute()
        }
    }

    fun list(): Collection<UserSelection> {
        context.use { context ->
            return selectionMapper(context).fetchInto(UserSelection::class.java)
        }
    }

    fun create(id: String, user: String, selection: String) {
        context.use { context ->
            context.insertInto(
                    USER_SELECTION,
                    USER_SELECTION.ID,
                    USER_SELECTION.SWAMI_USER,
                    USER_SELECTION.SELECTION
            ).values(
                    id,
                    user,
                    selection
            ).execute()
        }
    }

}