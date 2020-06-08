package swami2020.game

import com.jondejong.swami.model.tables.Selection.SELECTION
import swami2020.app.SwamiRepository

class SelectionRepository : SwamiRepository() {

    fun create(selection: NewSelection) {
        context.use { context ->
            context.insertInto(
                    SELECTION,
                    SELECTION.ID,
                    SELECTION.GAME,
                    SELECTION.HOME,
                    SELECTION.FAVORITE,
                    SELECTION.TEAM
            ).values(
                    selection.id.toString(),
                    selection.game.toString(),
                    selection.home,
                    selection.favorite,
                    selection.team.toString()
            ).execute()
        }
    }

    fun deleteByGame(game: String) {
        context.use { context ->
            context.deleteFrom(SELECTION)
                    .where(SELECTION.GAME.eq(game))
                    .execute()
        }
    }
}