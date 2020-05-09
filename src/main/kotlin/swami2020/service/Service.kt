package swami2020.service

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import swami2020.dto.DefaultableBuilder
import swami2020.exception.ItemNotFoundException
import java.util.*

open class Service {

    fun <I, O, B : DefaultableBuilder<O>> executeLoad (
            input: I,
            operation: (I) -> O,
            builder: B,
            id: UUID = UUID.randomUUID()
    ) : O {
        var output = builder.default(id)
        runBlocking {
            val job = GlobalScope.launch {
                output = operation(input)
            }
            job.join()
        }
        if(output == null) {
            throw ItemNotFoundException()
        }
        return output
    }
}
