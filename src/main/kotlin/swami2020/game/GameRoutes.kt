package swami2020.game

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.path
import swami2020.api.completeGameLens
import swami2020.api.createGameLens
import swami2020.api.gameLens
import swami2020.api.gameListLens
import swami2020.api.request.RequestHandler
import swami2020.api.response.ID
import swami2020.app.AppFactory
import java.util.*

class GameRoutes : RequestHandler() {

    private lateinit var gameService: GameService

    override fun setUp(factory: AppFactory) {
        super.setUp(factory)
        this.gameService = factory.gameService
    }

    private val gameListHandler = { _: Request ->
        gameListLens(gameService.list(), Response(Status.OK))
    }

    private val fetchGameHandler = { request: Request ->
        gameLens(gameService.fetch(UUID.fromString(request.path("id"))), Response(Status.OK))
    }

    private val gamesByWeekHandler = { request: Request ->
        gameListLens(gameService.listByWeek(UUID.fromString(request.path("id"))), Response(Status.OK))
    }

    private val deleteGameHandler = { request: Request ->
        gameService.delete(UUID.fromString(request.path("id")))
        Response(Status.OK)
    }

    private val createGameHandler = { request: Request ->
        idLens(
                ID(gameService.create(createGameLens(request))),
                Response(Status.OK)
        )
    }

    private val completeGameHandler = { request: Request ->
        gameService.completeGame(
                gameId = UUID.fromString(request.path("id")),
                completeGame = completeGameLens(request)
        )

        Response(Status.OK)
    }

    val routes = org.http4k.routing.routes(
            "/" bind Method.GET to gameListHandler,
            "/" bind Method.POST to createGameHandler,
            "/week/{id:.*}" bind Method.GET to gamesByWeekHandler,
            "/{id:.*}" bind Method.DELETE to deleteGameHandler,
            "/{id:.*}" bind Method.GET to fetchGameHandler,
            "/{id:.*}/score" bind Method.PUT to completeGameHandler
    )
}
