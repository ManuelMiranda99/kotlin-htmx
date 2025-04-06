package stsa.kotlin_htmx.models.CrateSkin

import org.jetbrains.exposed.sql.Table
import stsa.kotlin_htmx.models.crate.CrateTable
import stsa.kotlin_htmx.models.skin.SkinTable

object CrateSkinTable : Table("crates_skins") {
    val crate = reference("crate_id", CrateTable)
    val skin = reference("skin_id", SkinTable)
    override val primaryKey = PrimaryKey(crate, skin)
}