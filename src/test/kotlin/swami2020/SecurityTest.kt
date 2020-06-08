package swami2020

import swami2020.security.Password
import kotlin.test.*

class SecurityTest {

    val password = "P@ssw0rd1"
    val salt = "9ee3123b-69d1-4222-941d-e76781c70fa1"
    val expected = "mM3/YmzbGpwEiVg4pAbXA6B4rnoRy8T3ytVYaY+BnZw="

    @Test fun hasPassword() {
        val actual = Password.hash(password, salt)
        assertEquals(expected, actual)
    }
}