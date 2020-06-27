package swami2020.api.response

import java.util.*

data class UserWeekSelections(
        val userId: UUID,
        val firstName: String,
        val lastName: String,
        val week: Int,
        val weekId: UUID,
        val submitted: Boolean,
        val userSelections: Collection<UserSelection>
)

