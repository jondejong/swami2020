package swami2020.game

import swami2020.api.request.CreateGame
import java.math.BigDecimal
import java.util.*

data class NewGame(
        val id: UUID,
        val week: UUID,
        val complete: Boolean,
        val cancelled: Boolean,
        val spread: BigDecimal
) {
    companion object {
        val from = { request: CreateGame ->
            NewGame(
                    id = UUID.randomUUID(),
                    week = request.week,
                    complete = request.complete,
                    cancelled = request.cancelled,
                    spread = BigDecimal.valueOf(request.spread.toDouble())
            )
        }
    }
}