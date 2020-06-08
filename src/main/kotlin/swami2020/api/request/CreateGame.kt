package swami2020.api.request

import java.util.*

data class CreateGame(
        val selections: Collection<CreateSelection>,
        val complete: Boolean,
        val cancelled: Boolean,
        val week: UUID,
        val spread: Float

)