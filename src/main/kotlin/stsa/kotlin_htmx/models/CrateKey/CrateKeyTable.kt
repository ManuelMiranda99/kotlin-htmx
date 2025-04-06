package stsa.kotlin_htmx.models.CrateKey

import org.jetbrains.exposed.sql.Table
import stsa.kotlin_htmx.models.crate.CrateTable
import stsa.kotlin_htmx.models.key.KeyTable

object CrateKeyTable : Table("crates_keys") {
    val crate = reference("crate_id", CrateTable)
    val key = reference("key_id", KeyTable)
    override val primaryKey = PrimaryKey(crate, key)
}