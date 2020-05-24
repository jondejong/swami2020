package swami2020.api.request

import java.util.*

data class CreateGameRequest(
        val selections: Collection<CreateSelectionRequest>,
        val complete: Boolean,
        val cancelled: Boolean,
        val week: UUID,
        val spread: Float

) {
}