package stsa.kotlin_htmx.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.util.*

fun Application.configureBasicAuthentication() {
    val hashUserTable = createHashedUserTable()

    install(Authentication) {
        basic(name = "auth-basic") {
            validate { credentials ->
                hashUserTable.authenticate(credentials)
            }
        }
    }
}

fun createHashedUserTable () : UserHashedTableAuth {
    val digestFunction = getDigestFunction("SHA-256") { "ktor${it.length}" }

    return UserHashedTableAuth(
        digester = digestFunction,
        table = mapOf(
            "admin" to digestFunction("password"),
            "manuel" to digestFunction("password")
        )
    )
}