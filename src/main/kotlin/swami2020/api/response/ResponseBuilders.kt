package swami2020.api.response

import com.jondejong.swami.model.tables.pojos.SwamiUser
import java.util.*


val userFrom = {swamiUser: SwamiUser ->
    User(
            UUID.fromString(swamiUser.id),
            swamiUser.firstName,
            swamiUser.lastName,
            swamiUser.email
    )
}