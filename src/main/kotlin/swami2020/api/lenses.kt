package swami2020.api

import org.http4k.core.Body
import org.http4k.format.Jackson.auto
import swami2020.api.request.*
import swami2020.api.response.Team
import swami2020.api.response.User
import swami2020.api.response.UserWeekSelections
import swami2020.api.response.Week

// Week
val updateWeekCompleteLens = Body.auto<UpdateWeekComplete>().toLens()
val updateWeekReadyLens = Body.auto<UpdateWeekReady>().toLens()
val createWeekLens = Body.auto<CreateWeek>().toLens()
val weekListLens = Body.auto<Collection<Week>>().toLens()
val weekLens = Body.auto<Week>().toLens()

// Game
val gameListLens = Body.auto<Collection<Game>>().toLens()
val gameLens = Body.auto<Game>().toLens()
val createGameLens = Body.auto<CreateGame>().toLens()
val completeGameLens = Body.auto<CompleteGame>().toLens()

// Team
val teamLens = Body.auto<Team>().toLens()
val teamListLens = Body.auto<Collection<Team>>().toLens()

// Login
val loginRequestLens = Body.auto<swami2020.api.request.Login>().toLens()
val loginResponseLens = Body.auto<swami2020.api.response.Login>().toLens()

// User
val userLens = Body.auto<User>().toLens()
val userListLens = Body.auto<Collection<User>>().toLens()
val createUserLens = Body.auto<CreateUser>().toLens()

// Selections
val userWeekSelectionsLens = Body.auto<UserWeekSelections>().toLens()
val makeSelectionLens = Body.auto<MakeSelection>().toLens()
