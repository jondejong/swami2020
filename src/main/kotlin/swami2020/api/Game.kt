package swami2020.api

import swami2020.api.response.Selection
import java.util.*

data class Game(
        val id: UUID,
        val week: Int,
        val complete: Boolean,
        val cancelled: Boolean,
        val weekId: UUID,
        val spread: Float,
        var selections: Collection<Selection>
) {

}