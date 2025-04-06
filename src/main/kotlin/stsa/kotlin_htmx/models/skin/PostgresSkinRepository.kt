package stsa.kotlin_htmx.models.skin

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.or
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

    override suspend fun findBy(
        id: String?,
        name: String?,
        description: String?,
        image: String?,
        team: String?,
        crate: String?
    ): List<Skin> = suspendTransaction {
        SkinDAO.find{
            (SkinTable.id like "%$id%") or
            (SkinTable.name like "%$name%") or
            (SkinTable.description like "%$description%") or
            (SkinTable.image like "%$image%") or
            (SkinTable.team like "%$team%")
        }.map(::daoToSkinModel)
    }
}