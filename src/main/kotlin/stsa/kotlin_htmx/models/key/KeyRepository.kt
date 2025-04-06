package stsa.kotlin_htmx.models.key

interface KeyRepository {
    suspend fun allKeys(): List<Key>
    suspend fun addKey(key: Key)
}