package stsa.kotlin_htmx.models.crate

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import stsa.kotlin_htmx.models.CrateKey.CrateKeyTable
import stsa.kotlin_htmx.models.CrateSkin.CrateSkinTable
import stsa.kotlin_htmx.models.key.KeyDAO
import stsa.kotlin_htmx.models.key.daoToKeyModel
import stsa.kotlin_htmx.models.skin.SkinDAO
import stsa.kotlin_htmx.models.skin.daoToSkinModel

object CrateTable: IdTable<String>("crates") {
    override val id = varchar("id", 100).entityId().uniqueIndex()
    val name = varchar("name", 100)
    val description = varchar("description", 1000).nullable()
    val image = varchar("image", 1000)
}

class CrateDAO(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, CrateDAO>(CrateTable)

    var name by CrateTable.name
    var description by CrateTable.description
    var image by CrateTable.image

    var keys by KeyDAO via CrateKeyTable
    var skins by SkinDAO via CrateSkinTable
}

fun daoToCrateModel(dao: CrateDAO) = Crate(
    id = dao.id.value,
    name = dao.name,
    description = dao.description,
    image = dao.image,
    keys = dao.keys.map { daoToKeyModel(it) },
    skins = dao.skins.map { daoToSkinModel(it) }
)