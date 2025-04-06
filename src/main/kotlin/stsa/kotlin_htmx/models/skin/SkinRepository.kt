package stsa.kotlin_htmx.models.skin

interface SkinRepository {
    suspend fun allSkins(): List<Skin>
    suspend fun addSkin(skin: Skin)
}