package swami2020.dto

import java.util.*

interface DefaultableBuilder<T> {
    fun default() : T
    fun default(id: UUID) : T
}