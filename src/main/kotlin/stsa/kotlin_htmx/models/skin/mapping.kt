package stsa.kotlin_htmx.models.skin

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import stsa.kotlin_htmx.models.CrateSkin.CrateSkinTable
import stsa.kotlin_htmx.models.crate.CrateDAO

object SkinTable: IdTable<String>("skins") {
    override val id = varchar("id", 100).entityId().uniqueIndex()
    val name = varchar("name", 100)
    val description = varchar("description", 1000)
    val team = varchar("team", 100)
    val image = varchar("image", 1000)
}

class SkinDAO(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, SkinDAO>(SkinTable)

    var name by SkinTable.name
    var description by SkinTable.description
    var team by SkinTable.team
    var image by SkinTable.image

    var crates by CrateDAO via CrateSkinTable
}

fun daoToSkinModel(dao: SkinDAO) = Skin(
    dao.id.value,
    dao.name,
    dao.description,
    dao.team,
    dao.image,
    dao.crates.map { it.name }
)