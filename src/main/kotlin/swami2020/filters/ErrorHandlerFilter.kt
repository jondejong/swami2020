package swami2020.filters

import org.http4k.core.*
import swami2020.exception.ItemNotFoundException

class ErrorHandlerFilter {
    companion object {
        val errorFilter = Filter { next: HttpHandler ->
            var response: Response
            { request: Request ->
                try {
                    response = next(request)
                } catch(e: ItemNotFoundException) {
                    response = Response(Status.NOT_FOUND)
                } catch(e: IllegalArgumentException) {
                    response = Response(Status.BAD_REQUEST)
                } catch(t: Throwable) {
                    response = Response(Status.INTERNAL_SERVER_ERROR)
                }
                response
            }
        }
    }
}