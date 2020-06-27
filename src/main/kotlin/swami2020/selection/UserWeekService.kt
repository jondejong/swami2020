package swami2020.selection

import com.jondejong.swami.model.tables.pojos.UserWeek
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import swami2020.exception.IllegalDataStateException
import java.util.*

class UserWeekService : SwamiConfigurable {

    lateinit var userWeekRepository: UserWeekRepository

    override fun setUp(factory: AppFactory) {
        this.userWeekRepository = factory.userWeekRepository
    }

    fun isSubmitted(week: UUID, user: UUID): Boolean {
        val weeks = userWeekRepository.fetch(week.toString(), user.toString())
        if (weeks.size > 1) throw IllegalDataStateException()
        return if (weeks.isEmpty()) false else weeks.first().submitted
    }

    fun updateSubmitted(week: UUID, user: UUID, submitted: Boolean) {
        val weeks = userWeekRepository.fetch(week.toString(), user.toString())
        if (weeks.size > 1) throw IllegalDataStateException()

        if (weeks.isEmpty()) {
            userWeekRepository.create(UserWeek(UUID.randomUUID().toString(), user.toString(), week.toString(), submitted))
        } else {
            userWeekRepository.updateSubmitted(weeks.first().id, submitted)
        }
    }

    fun list(): Collection<UserWeek> {
        return userWeekRepository.list()
    }

    fun delete(id: UUID) {
        return userWeekRepository.delete(id.toString())
    }
}