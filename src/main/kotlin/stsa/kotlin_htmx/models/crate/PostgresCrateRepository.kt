package stsa.kotlin_htmx.models.crate

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import stsa.kotlin_htmx.models.CrateKey.CrateKeyTable
import stsa.kotlin_htmx.models.CrateSkin.CrateSkinTable
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