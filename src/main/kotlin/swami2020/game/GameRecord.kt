package swami2020.game

import java.util.*

data class GameRecord(
        val id: UUID,
        val week: Int,
        val spread: Float,
        val weekId: UUID,
        val complete: Boolean,
        val cancelled: Boolean,
        val selectionId: UUID,
        val selectionTeamId: UUID,
        val selectionTeamName: String,
        val selectionTeamNickName: String,
        val selectionHome: Boolean,
        val selectionFavorite: Boolean,
        val selectionConference: String,
        val selectionScore: Int?
)