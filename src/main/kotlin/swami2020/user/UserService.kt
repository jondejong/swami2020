package swami2020.user

import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import swami2020.response.userFrom
import swami2020.response.User
import java.util.*

class UserService() : SwamiConfigurable {

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
}