package swami2020.user

import com.jondejong.swami.model.tables.pojos.SwamiUser
import java.util.*

data class AuthenticatedUser(
        val id: UUID,
        val firstName: String,
        val lastName: String,
        val email: String,
        val roles: Collection<String>
) {

    companion object {
        private const val ADMIN_ROLE = "ADMIN"

        val from = { swamiUser: SwamiUser ->
            AuthenticatedUser(
                    id = UUID.fromString(swamiUser.id),
                    firstName = swamiUser.firstName,
                    lastName = swamiUser.lastName,
                    email = swamiUser.email,
                    roles = setOf()
            )
        }
    }

    fun isAdmin(): Boolean {
        return roles.contains(AuthenticatedUser.ADMIN_ROLE)
    }

}