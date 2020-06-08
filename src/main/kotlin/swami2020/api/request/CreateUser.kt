package swami2020.api.request

data class CreateUser(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String
)