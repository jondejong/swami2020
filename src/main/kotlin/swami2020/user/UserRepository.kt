package swami2020.user

import com.jondejong.swami.model.tables.SwamiUser.SWAMI_USER
import com.jondejong.swami.model.tables.pojos.SwamiUser
import org.jooq.DSLContext
import swami2020.app.SwamiRepository
import swami2020.exception.ItemNotFoundException
import java.util.*

class UserRepository : SwamiRepository() {

    private val userMapper = { context: DSLContext ->
        context.select(
                SWAMI_USER.ID,
                SWAMI_USER.FIRST_NAME,
                SWAMI_USER.LAST_NAME,
                SWAMI_USER.EMAIL,
                SWAMI_USER.PASSWORD,
                SWAMI_USER.SALT,
                SWAMI_USER.TOKEN
        ).from(SWAMI_USER)
    }

    fun list(): Collection<SwamiUser> {
        context.use { context ->
            return userMapper(context)
                    .fetchInto(SwamiUser::class.java)
        }
    }

    fun fetch(id: UUID): SwamiUser {
        context.use { context ->
            val users = userMapper(context)
                    .where(SWAMI_USER.ID.eq(id.toString()))
                    .fetchInto(SwamiUser::class.java)

            if (users?.size != 1) {
                throw ItemNotFoundException()
            }
            return users[0]
        }
    }

    fun fetchByEmail(emaail: String): SwamiUser {
        context.use { context ->
            val users = userMapper(context)
                    .where(SWAMI_USER.EMAIL.eq(emaail))
                    .fetchInto(SwamiUser::class.java)

            if (users?.size != 1) {
                throw ItemNotFoundException()
            }
            return users[0]
        }
    }

    fun fetchByToken(token: String): SwamiUser {
        context.use { context ->
            val users = userMapper(context)
                    .where(SWAMI_USER.TOKEN.eq(token))
                    .fetchInto(SwamiUser::class.java)

            if (users?.size != 1) {
                throw ItemNotFoundException()
            }
            return users[0]
        }
    }

    fun updateToken(id: UUID, token: String) {
        context.use { context ->
            context.update(SWAMI_USER)
                    .set(SWAMI_USER.TOKEN, token)
                    .where(SWAMI_USER.ID.eq(id.toString()))
                    .execute()
        }
    }

    fun save(swamiUser: SwamiUser) {
        context.use { context ->
            context.insertInto(
                    SWAMI_USER,
                    SWAMI_USER.ID,
                    SWAMI_USER.FIRST_NAME,
                    SWAMI_USER.LAST_NAME,
                    SWAMI_USER.EMAIL,
                    SWAMI_USER.PASSWORD,
                    SWAMI_USER.SALT
            ).values(
                    swamiUser.id,
                    swamiUser.firstName,
                    swamiUser.lastName,
                    swamiUser.email,
                    swamiUser.password,
                    swamiUser.salt
            ).execute()
        }
    }

    fun delete(id: String) {
        context.use { context ->
            context.deleteFrom(SWAMI_USER)
                    .where(SWAMI_USER.ID.eq(id))
                    .execute()
        }
    }
}