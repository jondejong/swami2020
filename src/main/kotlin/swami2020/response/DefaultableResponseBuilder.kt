package swami2020.response

import java.util.*

interface DefaultableResponseBuilder<T> {
    fun default() : T
    fun default(id: UUID) : T
}