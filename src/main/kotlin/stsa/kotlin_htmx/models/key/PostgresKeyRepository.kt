package stsa.kotlin_htmx.models.key

import org.jetbrains.exposed.sql.or
import stsa.kotlin_htmx.models.suspendTransaction

class PostgresKeyRepository: KeyRepository {
    override suspend fun allKeys(): List<Key> = suspendTransaction { KeyDAO.all().map(::daoToKeyModel) }

    override suspend fun addKey(key: Key): Unit = suspendTransaction {
        KeyDAO.new(key.id) {
            name = key.name
            description = key.description
            image = key.image
        }
    }

    override suspend fun findBy(
        id: String?,
        name: String?,
        description: String?,
        image: String?,
        crates: String?
    ): List<Key> = suspendTransaction {
        KeyDAO.find{
            (KeyTable.id like "%$id%") or
            (KeyTable.name like "%$name%") or
            (KeyTable.description like "%$description%") or
            (KeyTable.image like "%$image%")
        }.map(::daoToKeyModel)
    }
}