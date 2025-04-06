package stsa.kotlin_htmx.models.skin

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import stsa.kotlin_htmx.models.CrateSkin.CrateSkinTable
import stsa.kotlin_htmx.models.suspendTransaction

class PostgresSkinRepository: SkinRepository {
    init {
        transaction {
            SchemaUtils.create(SkinTable)
            SchemaUtils.create(CrateSkinTable)
        }
    }

    override suspend fun allSkins(): List<Skin> = suspendTransaction { SkinDAO.all().map(::daoToSkinModel) }

    override suspend fun addSkin(skin: Skin): Unit = suspendTransaction {
        SkinDAO.new(skin.id) {
            name = skin.name
            description = skin.description
            team = skin.team
            image = skin.image
        }
    }
}