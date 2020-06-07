package swami2020.game

import jdk.jshell.spi.ExecutionControl
import swami2020.api.Game
import swami2020.api.request.CreateGameRequest
import swami2020.api.response.builder.RecordGroup
import swami2020.api.response.builder.gameFrom
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import java.util.*
import kotlin.collections.HashMap

class GameService() : SwamiConfigurable {
    private lateinit var gameRepository: GameRepository
    private lateinit var selectionRepository: SelectionRepository

    override fun setUp(factory: AppFactory) {
        this.gameRepository = factory.gameRepository
        this.selectionRepository = factory.selectionRepository
    }

    fun listByWeek(weekId: UUID): Collection<Game> {
        return processRecords(gameRepository.listByWeek(weekId.toString()))
    }

    fun list(): Collection<Game> {
        return processRecords(gameRepository.list())
    }

    fun create(createGameRequest: CreateGameRequest): UUID {
        val game = NewGame.from(createGameRequest)
        gameRepository.create(game)
        createGameRequest.selections.map {
            selectionRepository.create(NewSelection.from(game.id, it))
        }
        return game.id
    }

    fun delete(id: UUID) {
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
        return processRecords(gameRepository.fetch(id.toString())).first()
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