package swami2020.game

import swami2020.api.request.CreateSelection
import java.util.*

data class NewSelection(
        val id: UUID,
        val game: UUID,
        val team: UUID,
        val home: Boolean,
        val favorite: Boolean
) {
    companion object {
        val from = { game: UUID, request: CreateSelection ->
            NewSelection(
                    id = UUID.randomUUID(),
                    game = game,
                    team = request.team,
                    home = request.home,
                    favorite = request.favorite
            )
        }
    }
}