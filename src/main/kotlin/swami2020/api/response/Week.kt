package swami2020.api.response

import swami2020.api.Game
import java.util.*

data class Week(val id: UUID, val games: Collection<Game>?) {}