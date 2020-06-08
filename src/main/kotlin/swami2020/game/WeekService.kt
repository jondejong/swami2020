package swami2020.game

import com.jondejong.swami.model.tables.pojos.Week
import swami2020.api.request.CreateWeek
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import java.util.*

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
                Week(id.toString(), false, false, createWeek.number)
        )

        return id
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

    fun delete(id: UUID) {
        weekRepository.delete(id.toString())
    }
}