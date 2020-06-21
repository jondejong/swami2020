package swami2020.game

import com.jondejong.swami.model.tables.pojos.Week
import swami2020.api.request.CreateWeek
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import swami2020.exception.ItemNotFoundException
import java.util.*
import kotlin.Comparator

class WeekService : SwamiConfigurable {

    lateinit var weekRepository: WeekRepository

    override fun setUp(factory: AppFactory) {
        this.weekRepository = factory.weekRepository
    }

    fun list(): Collection<Week> {
        return weekRepository.list()
    }

    fun create(createWeek: CreateWeek): UUID {
        val id = UUID.randomUUID()

        weekRepository.create(
                Week(id.toString(), false, false, false, createWeek.number)
        )

        return id
    }

    fun fetchByNumber(number: Int): Week {
        val weeks = weekRepository.fetchByNumber(number)
        if (weeks.size != 1) {
            throw ItemNotFoundException()
        }
        return weeks.first()
    }

    fun fetchCurrent(): Week {
        val readyWeeks = weekRepository.fetchReadyNotCompleted()
        if (readyWeeks.size > 1) {
            throw IllegalStateException("More then one week is ready but not completed")
        }
        if (readyWeeks.size == 1) {
            return readyWeeks.first()
        }

        val completedWeeks = weekRepository.fetchCompleted()
                .sortedWith(Comparator<Week> { o1: Week, o2: Week ->
                    o2.number.compareTo(o1.number)
                })

        if (completedWeeks.isEmpty()) {
            throw ItemNotFoundException()
        }

        return completedWeeks.first()

    }

    fun updateComplete(id: UUID, status: Boolean) {
        weekRepository.updateComplete(
                id = id.toString(),
                status = status
        )
    }

    fun updateReady(id: UUID, status: Boolean) {
        weekRepository.updateReady(
                id = id.toString(),
                status = status
        )
    }

    fun updateLocked(id: UUID, status: Boolean) {
        weekRepository.updateLocked(
                id = id.toString(),
                status = status
        )
    }

    fun delete(id: UUID) {
        weekRepository.delete(id.toString())
    }
}