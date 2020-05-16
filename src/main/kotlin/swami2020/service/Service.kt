package swami2020.service

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import swami2020.response.DefaultableResponseBuilder
import swami2020.exception.ItemNotFoundException
import java.util.*

open class Service {

    /*
    Overcomplicated attempt at making generic non-bocking I/O
     */
    fun <I, O, B : DefaultableResponseBuilder<O>> executeLoad (
            input: I,
            operation: (I) -> O,
            builder: B,
            id: UUID = UUID.randomUUID()
    ) : O {
        var output : O? = null
        runBlocking {
            GlobalScope.launch {
                output = operation(input)
            }.join()
        }
        if(output == null) {throw ItemNotFoundException()}

        /*
        This feels bad. The builder is only passed in to be able to gaurantee that
        output will never be null. But it will never be null.
         */
        return output ?: builder.default(id)
    }
}
