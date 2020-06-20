package swami2020.selection

import java.util.*

data class UserSelectionRecord(
        val id: UUID,
        val selectionId: UUID,
        val home: Boolean,
        val favorite: Boolean,
        val score: Int?,
        val teamId: UUID,
        val teamName: String,
        val teamNickName: String,
        val conference: String
)