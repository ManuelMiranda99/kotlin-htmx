package stsa.kotlin_htmx.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import stsa.kotlin_htmx.models.CrateKey.CrateKeyTable
import stsa.kotlin_htmx.models.CrateSkin.CrateSkinTable
import stsa.kotlin_htmx.models.agent.AgentTable
import stsa.kotlin_htmx.models.crate.CrateTable
import stsa.kotlin_htmx.models.key.KeyTable
import stsa.kotlin_htmx.models.skin.SkinTable

fun Application.configureDatabase() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/my_database",
        user = "admin",
        password = "secret"
    )

    transaction {
        SchemaUtils.drop(
            CrateKeyTable, CrateSkinTable, AgentTable, KeyTable, SkinTable, CrateTable
        )
        SchemaUtils.create(
            CrateTable, AgentTable, KeyTable, SkinTable, CrateKeyTable, CrateSkinTable
        )
    }
}