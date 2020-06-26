package swami2020.selection

import java.util.*

data class UserWeekRequest(val loggedInUser: UUID, val queryUser: UUID, val week: Int)