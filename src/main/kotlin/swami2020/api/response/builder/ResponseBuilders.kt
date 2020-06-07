package swami2020.api.response.builder

import com.jondejong.swami.model.tables.pojos.SwamiUser
import swami2020.api.Game
import swami2020.api.response.Selection
import swami2020.api.response.Team
import swami2020.api.response.User
import swami2020.game.GameRecord
import java.util.*

val userFrom = { swamiUser: SwamiUser ->
    User(
            UUID.fromString(swamiUser.id),
            swamiUser.firstName,
            swamiUser.lastName,
            swamiUser.email
    )
}

val gameFrom = { recordGroup: RecordGroup ->
    Game(
            id = recordGroup.record1.id,
            week = recordGroup.record1.week,
            weekId = recordGroup.record1.weekId,
            cancelled = recordGroup.record1.cancelled,
            complete = recordGroup.record1.complete,
            spread = recordGroup.record1.spread,
            selections = setOf(
                    selectionFrom(recordGroup.record1),
                    selectionFrom(recordGroup.record2!!)
            )
    )
}

val selectionFrom = { gameRecord: GameRecord ->
    Selection(
            id = gameRecord.selectionId,
            favorite = gameRecord.selectionFavorite,
            home = gameRecord.selectionHome,
            team = teamFrom(gameRecord),
            score = null
    )
}

val teamFrom = { gameRecord: GameRecord ->
    Team(
            id = gameRecord.selectionTeamId,
            name = gameRecord.selectionTeamName,
            nickName = gameRecord.selectionTeamNickName,
            conference = gameRecord.selectionConference
    )
}
