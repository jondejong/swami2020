package swami2020.user

import swami2020.api.response.LoginResponse
import swami2020.api.response.builder.userFrom
import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable
import swami2020.exception.ItemNotFoundException
import swami2020.exception.UnauthenticatedException
import swami2020.security.Password
import java.util.*

class LoginService : SwamiConfigurable {

    private lateinit var userRepository: UserRepository

    override fun setUp(factory: AppFactory) {
        this.userRepository = factory.userRepository
    }

    fun validateUser(username: String, password: String): LoginResponse {
        try {
            val swamiUser = userRepository.fetchByEmail(username)
            Password.validate(swamiUser.password, password, swamiUser.salt)
            val token = UUID.randomUUID().toString()
            userRepository.updateToken(
                    id = UUID.fromString(swamiUser.id),
                    token = token
            )
            return LoginResponse(
                    userFrom(swamiUser),
                    token
            )
        } catch (itemNotFoundException: ItemNotFoundException) {
            throw UnauthenticatedException()
        }
    }
}