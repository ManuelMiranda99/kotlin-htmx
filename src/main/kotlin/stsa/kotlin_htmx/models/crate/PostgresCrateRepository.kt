package stsa.kotlin_htmx.models.crate

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.transaction
import stsa.kotlin_htmx.models.CrateKey.CrateKeyTable
import stsa.kotlin_htmx.models.CrateSkin.CrateSkinTable
import stsa.kotlin_htmx.models.key.KeyTable
import stsa.kotlin_htmx.models.skin.SkinTable
import stsa.kotlin_htmx.models.suspendTransaction

class PostgresCrateRepository: CrateRepository {
    init {
        transaction {
            SchemaUtils.create(CrateTable)
        }
    }

    override suspend fun allCrates(): List<Crate> = suspendTransaction { CrateDAO.all().map(::daoToCrateModel) }

    override suspend fun addCrate(crate: Crate): Unit = suspendTransaction {
        CrateDAO.new(crate.id) {
            name = crate.name
            description = crate.description
            image = crate.image
        }
    }

    override suspend fun findBy(
        id: String?,
        name: String?,
        description: String?,
        image: String?,
        keys: String?,
        skins: String?
    ): List<Crate> = suspendTransaction {
        CrateDAO.find {
            (CrateTable.id like "%$id%") or
            (CrateTable.name like "%$name%") or
            (CrateTable.description like "%$description%") or
            (CrateTable.image like "$image")
        }.map(::daoToCrateModel)
    }

    override suspend fun insertCrateKeyRelation(keyId: String, crateId: String) = suspendTransaction {
        val crate = CrateDAO.findById(crateId) ?: return@suspendTransaction

        CrateKeyTable.insert {
            it[this.crate] = crate.id
            it[this.key] = keyId
        }
    }

    override suspend fun insertCrateSkinRelation(skinId: String, crateId: String) = suspendTransaction {
        val crate = CrateDAO.findById(crateId) ?: return@suspendTransaction

        CrateSkinTable.insert {
            it[this.crate] = crate.id
            it[this.skin] = skinId
        }
    }
}