package swami2020.api.response.builder

import com.jondejong.swami.model.tables.pojos.SwamiUser
import swami2020.api.Game
import swami2020.api.response.*
import swami2020.game.GameRecord
import swami2020.selection.UserSelectionRecord
import swami2020.selection.UserWeekRecordCollection
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
                    selectionFromGameRecord(recordGroup.record1),
                    selectionFromGameRecord(recordGroup.record2!!)
            )
    )
}

val selectionFromGameRecord = { gameRecord: GameRecord ->
    Selection(
            id = gameRecord.selectionId,
            favorite = gameRecord.selectionFavorite,
            home = gameRecord.selectionHome,
            team = teamFromGameRecord(gameRecord),
            score = gameRecord.selectionScore
    )
}

val selectionFrom = { record: UserSelectionRecord ->
    Selection(
            id = record.id,
            favorite = record.favorite,
            home = record.home,
            score = record.score,
            team = teamFromSelectionRecord(record)
    )
}

val teamFromSelectionRecord = { record: UserSelectionRecord ->
    Team(
            id = record.teamId,
            name = record.teamName,
            nickName = record.teamNickName,
            conference = record.conference
    )
}

val teamFromGameRecord = { gameRecord: GameRecord ->
    Team(
            id = gameRecord.selectionTeamId,
            name = gameRecord.selectionTeamName,
            nickName = gameRecord.selectionTeamNickName,
            conference = gameRecord.selectionConference
    )
}

val userWeekSelectionsFrom = { record: UserWeekRecordCollection ->
    UserWeekSelections(
            userId = record.user.id,
            firstName = record.user.firstName,
            lastName = record.user.lastName,
            week = record.week.number,
            weekId = UUID.fromString(record.week.id),
            userSelections = record.userSelectionRecords.map { userSelectionFrom(it) }
    )
}

val userSelectionFrom = { record: UserSelectionRecord ->
    UserSelection(
            id = record.id,
            selection = selectionFrom(record)
    )
}

