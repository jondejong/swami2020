package swami2020.game

import com.jondejong.swami.model.tables.Selection.SELECTION
import swami2020.app.SwamiRepository
import swami2020.database.RepositoryId

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

    fun listIdsByGame(game: String): Collection<RepositoryId> {
        context.use { context ->
            return context.select(SELECTION.ID)
                    .from(SELECTION)
                    .where(SELECTION.GAME.eq(game))
                    .fetchInto(RepositoryId::class.java)
        }
    }

    fun deleteByGame(game: String) {
        context.use { context ->
            context.deleteFrom(SELECTION)
                    .where(SELECTION.GAME.eq(game))
                    .execute()
        }
    }

    fun setScore(id: String, score: Int) {
        context.use { context ->
            context.update(SELECTION)
                    .set(SELECTION.SCORE, score)
                    .where(SELECTION.ID.eq(id))
                    .execute()
        }
    }
}