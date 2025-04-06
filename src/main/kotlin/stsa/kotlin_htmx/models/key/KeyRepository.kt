package stsa.kotlin_htmx.models.key

interface KeyRepository {
    suspend fun allKeys(): List<Key>
    suspend fun addKey(key: Key)

    suspend fun findBy(
        id: String? = null,
        name: String? = null,
        description: String? = null,
        image: String? = null,
        crates: String? = null,
    ) : List<Key>
}