package stsa.kotlin_htmx.models.crate

class FakeCrateRepository : CrateRepository {
    private val crates = mutableListOf<Crate>()

    override suspend fun allCrates(): List<Crate> = crates

    override suspend fun addCrate(crate: Crate) {
        crates.add(crate)
    }

    override suspend fun findBy(
        id: String?, name: String?, description: String?, image: String?, keys: String?, skins: String?
    ): List<Crate> {
        return crates.filter {
            (id == null || it.id == id) &&
            (name == null || it.name.contains(name, ignoreCase = true)) &&
            (description == null || it.description?.contains(description, ignoreCase = true) == true) &&
            (image == null || it.image == image) && (keys == null || it.keys.contains(keys)) && (skins == null || it.skins.contains(skins))
        }
    }

    override suspend fun insertCrateKeyRelation(keyId: String, crateId: String) {}

    override suspend fun insertCrateSkinRelation(skinId: String, crateId: String) {}
}
