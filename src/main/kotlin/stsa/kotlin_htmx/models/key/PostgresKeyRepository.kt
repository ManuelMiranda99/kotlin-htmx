package stsa.kotlin_htmx.models.key

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import stsa.kotlin_htmx.models.CrateKey.CrateKeyTable
import stsa.kotlin_htmx.models.suspendTransaction

class PostgresKeyRepository: KeyRepository {
    init {
        transaction {
            SchemaUtils.create(KeyTable)
            SchemaUtils.create(CrateKeyTable)
        }
    }

    override suspend fun allKeys(): List<Key> = suspendTransaction { KeyDAO.all().map(::daoToKeyModel) }

    override suspend fun addKey(key: Key): Unit = suspendTransaction {
        KeyDAO.new(key.id) {
            name = key.name
            description = key.description
            image = key.image
        }
    }
}