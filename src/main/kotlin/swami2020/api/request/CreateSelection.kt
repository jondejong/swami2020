package swami2020.api.request

import java.util.*

data class CreateSelection(
        val team: UUID,
        val home: Boolean,
        val favorite: Boolean
)