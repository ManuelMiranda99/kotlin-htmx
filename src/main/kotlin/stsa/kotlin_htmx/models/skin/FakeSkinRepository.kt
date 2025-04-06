package stsa.kotlin_htmx.models.skin

class FakeSkinRepository : SkinRepository {
    private val skins = mutableListOf<Skin>()

    override suspend fun allSkins(): List<Skin> = skins

    override suspend fun addSkin(skin: Skin) {
        skins.add(skin)
    }

    override suspend fun findBy(
        id: String?, name: String?, description: String?, image: String?, team: String?, crate: String?
    ): List<Skin> {
        return skins.filter {
            (id == null || it.id == id) &&
            (name == null || it.name.contains(name, ignoreCase = true)) &&
            (description == null || it.description.contains(description, ignoreCase = true)) &&
            (image == null || it.image == image) && (team == null || it.team == team) &&
            (crate == null || it.crates.contains(crate))
        }
    }
}
