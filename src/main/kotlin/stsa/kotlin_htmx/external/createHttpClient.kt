package stsa.kotlin_htmx.external

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.jackson.*

val client = HttpClient(CIO) {
    install(Logging)
    install(ContentNegotiation) {
        jackson()
    }

}