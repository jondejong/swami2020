package swami2020.selection

import com.jondejong.swami.model.tables.UserWeek.USER_WEEK
import com.jondejong.swami.model.tables.pojos.UserWeek
import swami2020.app.SwamiRepository

class UserWeekRepository : SwamiRepository() {

    fun list(): Collection<UserWeek> {
        context.use { context ->
            return context.select(
                    USER_WEEK.ID,
                    USER_WEEK.SWAMI_USER,
                    USER_WEEK.WEEK,
                    USER_WEEK.SUBMITTED
            ).from(USER_WEEK).fetchInto(UserWeek::class.java)
        }
    }

    fun delete(id: String) {
        context.use {
            context.deleteFrom(USER_WEEK).where(USER_WEEK.ID.eq(id)).execute()
        }
    }

    fun fetch(week: String, user: String): Collection<UserWeek> {
        context.use { context ->
            return context.select(
                    USER_WEEK.ID,
                    USER_WEEK.SWAMI_USER,
                    USER_WEEK.WEEK,
                    USER_WEEK.SUBMITTED
            ).from(USER_WEEK)
                    .where(USER_WEEK.SWAMI_USER.eq(user))
                    .and(USER_WEEK.WEEK.eq(week))
                    .fetchInto(UserWeek::class.java)
        }
    }

    fun create(userWeek: UserWeek) {
        context.use { context ->
            context.insertInto(
                    USER_WEEK,
                    USER_WEEK.ID,
                    USER_WEEK.SWAMI_USER,
                    USER_WEEK.WEEK,
                    USER_WEEK.SUBMITTED
            ).values(
                    userWeek.id,
                    userWeek.swamiUser,
                    userWeek.week,
                    userWeek.submitted
            ).execute()
        }
    }

    fun updateSubmitted(id: String, state: Boolean) {
        context.use { context ->
            context.update(USER_WEEK)
                    .set(USER_WEEK.SUBMITTED, state)
                    .where(USER_WEEK.ID.eq(id))
                    .execute()
        }
    }
}
