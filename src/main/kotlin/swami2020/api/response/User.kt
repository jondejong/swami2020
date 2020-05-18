package swami2020.api.response

import java.util.*

data class User(
        val id: UUID,
        val firstName: String,
        val lastName: String,
        val email: String
) {

}