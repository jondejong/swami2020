package swami2020.selection

import com.jondejong.swami.model.tables.pojos.Week
import swami2020.api.response.User

data class UserWeekRecordCollection(
        val user: User,
        val week: Week,
        val submitted: Boolean,
        val userSelectionRecords: Collection<UserSelectionRecord>
)