package stsa.kotlin_htmx.models.crate

interface CrateRepository {
    suspend fun allCrates(): List<Crate>
    suspend fun addCrate(crate: Crate)

    suspend fun insertCrateKeyRelation(keyId: String, crateId: String)
    suspend fun insertCrateSkinRelation(skinId: String, crateId: String)
}