package swami2020.game

import com.jondejong.swami.model.tables.Week.WEEK
import com.jondejong.swami.model.tables.pojos.Week
import org.jooq.DSLContext
import swami2020.app.SwamiRepository

class WeekRepository : SwamiRepository() {

    private val weekContextMapper = { context: DSLContext ->
        context.select(
                WEEK.ID,
                WEEK.READY,
                WEEK.COMPLETE,
                WEEK.LOCKED,
                WEEK.NUMBER
        ).from(WEEK)
    }

    fun list(): Collection<Week> {
        context.use { context ->
            return weekContextMapper(context).fetchInto(Week::class.java)
        }
    }

    fun fetchReadyNotCompleted(): Collection<Week> {
        context.use { context ->
            return weekContextMapper(context)
                    .where(WEEK.READY.eq(true))
                    .and(WEEK.COMPLETE.eq(false))
                    .fetchInto(Week::class.java)
        }
    }

    fun fetchCompleted(): Collection<Week> {
        context.use { context ->
            return weekContextMapper(context)
                    .where(WEEK.COMPLETE.eq(true))
                    .fetchInto(Week::class.java)
        }
    }

    fun fetchByNumber(number: Int): Collection<Week> {
        context.use { context ->
            return weekContextMapper(context)
                    .where(WEEK.NUMBER.eq(number))
                    .fetchInto(Week::class.java)
        }
    }

    fun create(week: Week) {
        context.use { context ->
            context.insertInto(
                    WEEK,
                    WEEK.ID,
                    WEEK.NUMBER,
                    WEEK.READY,
                    WEEK.LOCKED,
                    WEEK.COMPLETE
            ).values(
                    week.id,
                    week.number,
                    week.ready,
                    week.locked,
                    week.complete
            ).execute()
        }
    }

    fun delete(id: String) {
        context.use { context ->
            context.deleteFrom(WEEK).where(WEEK.ID.eq(id)).execute()
        }
    }

    fun updateComplete(id: String, status: Boolean) {
        context.use { context ->
            context.update(WEEK)
                    .set(WEEK.COMPLETE, status)
                    .where(WEEK.ID.eq(id))
                    .execute()

        }
    }

    fun updateReady(id: String, status: Boolean) {
        context.use { context ->
            context.update(WEEK)
                    .set(WEEK.READY, status)
                    .where(WEEK.ID.eq(id))
                    .execute()

        }
    }

    fun updateLocked(id: String, status: Boolean) {
        context.use { context ->
            context.update(WEEK)
                    .set(WEEK.LOCKED, status)
                    .where(WEEK.ID.eq(id))
                    .execute()

        }
    }
}