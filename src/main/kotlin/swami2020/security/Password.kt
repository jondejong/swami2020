package swami2020.security

import java.security.MessageDigest
import java.util.*

class Password {
    companion object {
        val hash = { password: String, salt: String ->
            sha256Hash("${salt}${password}")
        }

        private val sha256Hash = { text: String ->
            Base64.getEncoder().encodeToString(
                    MessageDigest.getInstance("SHA-256")
                            .digest(text.toByteArray())
            )
        }
    }
}