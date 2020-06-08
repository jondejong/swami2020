package swami2020.api.response

import java.util.*

data class Selection(
        val id: UUID,
        val team: Team,
        val home: Boolean,
        val favorite: Boolean,
        val score: Int?
)