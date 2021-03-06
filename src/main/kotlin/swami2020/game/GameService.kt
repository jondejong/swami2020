package swami2020.game

import jdk.jshell.spi.ExecutionControl
import swami2020.api.Game
import swami2020.api.request.CompleteGame
import swami2020.api.request.CreateGame
import swami2020.api.response.builder.RecordGroup
import swami2020.api.response.builder.gameFrom
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import swami2020.exception.ItemNotFoundException
import swami2020.selection.UserSelectionRepository
import java.util.*
import kotlin.collections.HashMap

class GameService : SwamiConfigurable {
    private lateinit var gameRepository: GameRepository
    private lateinit var selectionRepository: SelectionRepository
    private lateinit var userSelectionRepository: UserSelectionRepository

    override fun setUp(factory: AppFactory) {
        this.gameRepository = factory.gameRepository
        this.selectionRepository = factory.selectionRepository
        this.userSelectionRepository = factory.userSelectionRepository
    }

    fun listByWeek(weekId: UUID): Collection<Game> {
        return processRecords(gameRepository.listByWeek(weekId.toString()))
    }

    fun list(): Collection<Game> {
        return processRecords(gameRepository.list())
    }

    fun create(createGame: CreateGame): UUID {
        val game = NewGame.from(createGame)
        gameRepository.create(game)
        createGame.selections.map {
            selectionRepository.create(NewSelection.from(game.id, it))
        }
        return game.id
    }

    fun delete(id: UUID) {
        //TODO: Wrap this in a transaction
        selectionRepository.listIdsByGame(id.toString()).map {
            userSelectionRepository.deleteBySelection(it.id.toString())
        }
        gameRepository.delete(id.toString())
        selectionRepository.deleteByGame(id.toString())
    }

    fun updateScore() {
        throw ExecutionControl.NotImplementedException("Implement me")
    }

    fun cancel() {
        throw ExecutionControl.NotImplementedException("Implement me")
    }

    fun fetch(id: UUID): Game {
        val records = gameRepository.fetch((id.toString()))
        if (records.size != 2) {
            throw ItemNotFoundException()
        }
        return processRecords(records).first()
    }

    fun completeGame(gameId: UUID, completeGame: CompleteGame) {
        // Validate the game exists
        fetch(gameId)

        gameRepository.completeGame(gameId.toString())
        completeGame.selectionScores.map {
            selectionRepository.setScore(
                    id = it.selection.toString(),
                    score = it.score
            )
        }
    }

    private fun processRecords(records: Collection<GameRecord>): Collection<Game> {
        return records.fold(HashMap<UUID, RecordGroup>()) { accumulator, gameRecord ->
            if (accumulator.keys.contains(gameRecord.id)) {
                accumulator.get(gameRecord.id)?.record2 = gameRecord
            } else {
                accumulator.put(gameRecord.id, RecordGroup(gameRecord, null))
            }
            accumulator
        }.values.map {
            gameFrom(it)
        }
    }
}