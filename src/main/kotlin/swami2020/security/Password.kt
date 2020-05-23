package swami2020.security

import swami2020.exception.UnauthenticatedException
import java.security.MessageDigest
import java.util.*

object Password {

    val hash = { password: String, salt: String ->
        sha256Hash("${salt}${password}")
    }

    val validate = { actual: String, expected: String, salt: String ->
        if (actual != hash(expected, salt)) {
            throw UnauthenticatedException()
        }
    }

    private val sha256Hash = { text: String ->
        Base64.getEncoder().encodeToString(
                MessageDigest.getInstance("SHA-256")
                        .digest(text.toByteArray())
        )
    }
}