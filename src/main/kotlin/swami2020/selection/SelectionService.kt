package swami2020.selection

import com.jondejong.swami.model.tables.pojos.UserSelection
import swami2020.api.response.UserWeekSelections
import swami2020.api.response.builder.userWeekSelectionsFrom
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import swami2020.exception.IllegalDataStateException
import swami2020.exception.ItemNotFoundException
import swami2020.game.WeekService
import swami2020.user.UserService
import java.util.*

class SelectionService : SwamiConfigurable {

    lateinit var userSelectionRepository: UserSelectionRepository
    lateinit var userService: UserService
    lateinit var weekService: WeekService

    override fun setUp(factory: AppFactory) {
        this.userSelectionRepository = factory.userSelectionRepository
        this.userService = factory.userService
        this.weekService = factory.weekService
    }

    fun list(): Collection<UserSelection> {
        return userSelectionRepository.list()
    }

    fun listUserWeek(user: UUID, week: Int): UserWeekSelections {
        return userWeekSelectionsFrom(
                UserWeekRecordCollection(
                        user = userService.fetch(user),
                        week = weekService.fetchByNumber(week),
                        userSelectionRecords = userSelectionRepository.listByUserWeek(
                                user = user.toString(),
                                week = week
                        )
                )
        )
    }

    fun delete(id: UUID) {
        userSelectionRepository.delete(id.toString())
    }

    fun fetch(id: UUID): UserSelection {
        val selections = userSelectionRepository.fetch(id.toString())
        if (selections.isEmpty()) {
            throw ItemNotFoundException()
        }
        if (selections.size > 1) {
            throw IllegalDataStateException()
        }
        return selections.first()
    }

    fun createSelection(newUserSelection: NewUserSelection): UUID {
        val id = UUID.randomUUID()
        userSelectionRepository.create(
                id = id.toString(),
                user = newUserSelection.user.toString(),
                selection = newUserSelection.selection.toString()
        )
        return id
    }
}