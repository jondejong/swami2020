package swami2020

import org.http4k.core.Method
import org.http4k.core.Request
import swami2020.app.AppFactory

val SecureRequest = { method: Method, url: String, token: String ->
    Request(method, url).header(AppFactory.AUTHENTICATION_HEADER, token)
}
