package stsa.kotlin_htmx.models.skin

interface SkinRepository {
    suspend fun allSkins(): List<Skin>
    suspend fun addSkin(skin: Skin)
    suspend fun findBy(
        id: String? = null,
        name: String? = null,
        description: String? = null,
        image: String? = null,
        team: String? = null,
        crate: String? = null,
    ) : List<Skin>
}