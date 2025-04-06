package stsa.kotlin_htmx.models.key

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import stsa.kotlin_htmx.models.CrateKey.CrateKeyTable
import stsa.kotlin_htmx.models.crate.CrateDAO

object KeyTable: IdTable<String>("keys") {
    override val id = varchar("id", 100).entityId().uniqueIndex()
    val name = varchar("name", 100)
    val description = varchar("description", 1000)
    val image = varchar("image", 1000)
}

class KeyDAO(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, KeyDAO>(KeyTable)

    var name by KeyTable.name
    var description by KeyTable.description
    var image by KeyTable.image

    var crates by CrateDAO via CrateKeyTable
}

fun daoToKeyModel(dao: KeyDAO) = Key(
    dao.id.value,
    dao.name,
    dao.description,
    dao.image,
    dao.crates.map { it.name }
)