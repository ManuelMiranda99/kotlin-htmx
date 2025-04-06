package stsa.kotlin_htmx.models.crate

interface CrateRepository {
    suspend fun allCrates(): List<Crate>
    suspend fun addCrate(crate: Crate)

    suspend fun findBy(
        id: String? = null,
        name: String? = null,
        description: String? = null,
        image: String? = null,
        keys: String? = null,
        skins: String? = null,
    ) : List<Crate>

    suspend fun insertCrateKeyRelation(keyId: String, crateId: String)
    suspend fun insertCrateSkinRelation(skinId: String, crateId: String)
}