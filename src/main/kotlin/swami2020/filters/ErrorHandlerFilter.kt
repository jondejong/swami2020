package swami2020.filters

import org.http4k.core.*
import swami2020.exception.ItemNotFoundException
import swami2020.exception.UnauthenticatedException

object ErrorHandlerFilter {
    val errorFilter = Filter { next: HttpHandler ->
        { request: Request ->
            var response: Response
            try {
                response = next(request)
            } catch (e: ItemNotFoundException) {
                response = Response(Status.NOT_FOUND)
            } catch (e: IllegalArgumentException) {
                response = Response(Status.BAD_REQUEST)
            } catch (e: UnauthenticatedException) {
                response = Response(Status.UNAUTHORIZED)
            } catch (t: Throwable) {
                response = Response(Status.INTERNAL_SERVER_ERROR)
            }
            response
        }
    }
}