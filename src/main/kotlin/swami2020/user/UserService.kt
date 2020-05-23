package swami2020.user

import com.jondejong.swami.model.tables.pojos.SwamiUser
import swami2020.api.request.CreateUser
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import swami2020.api.response.userFrom
import swami2020.api.response.User
import swami2020.security.Password
import java.util.*

class UserService(): SwamiConfigurable {

    private lateinit var userRepository: UserRepository

    override fun setUp(factory: AppFactory) {
        this.userRepository = factory.userRepository
    }

    fun list(): Collection<User> {
        return userRepository.list().map { userFrom(it) }
    }

    fun fetch(id: UUID): User {
        return userFrom(userRepository.fetch(id))
    }

    fun fetchByToken(token: String): AuthenticatedUser {
        return AuthenticatedUser.from(userRepository.fetchByToken(token))
    }

    fun create(createUser: CreateUser) : User {
        val salt = UUID.randomUUID().toString()
        val swamiUser = SwamiUser(
                UUID.randomUUID().toString(),
                createUser.firstName,
                createUser.lastName,
                createUser.email,
                Password.hash(createUser.password, salt),
                salt,
                null
        )
        userRepository.save(swamiUser)
        return userFrom(swamiUser)
    }

    fun delete(id: UUID) {
        userRepository.delete(id.toString())
    }
}