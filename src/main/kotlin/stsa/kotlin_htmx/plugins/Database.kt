package stsa.kotlin_htmx.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/my_database",
        user = "admin",
        password = "secret"
    )
}