package swami2020.api.response

import java.util.*

data class Week(
        val id: UUID,
        val number: Int,
        val ready: Boolean,
        val complete: Boolean
) {
    companion object {
        val from = { week: com.jondejong.swami.model.tables.pojos.Week ->
            Week(
                    id = UUID.fromString(week.id),
                    number = week.number,
                    ready = week.ready,
                    complete = week.complete
            )
        }
    }
}
