package swami2020.api.request

import java.util.*

data class CreateSelectionRequest(
        val team: UUID,
        val home: Boolean,
        val favorite: Boolean
)