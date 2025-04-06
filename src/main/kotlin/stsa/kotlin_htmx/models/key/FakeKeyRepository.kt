package stsa.kotlin_htmx.models.key

class FakeKeyRepository : KeyRepository {
    private val keys = mutableListOf<Key>()

    override suspend fun allKeys(): List<Key> = keys

    override suspend fun addKey(key: Key) {
        keys.add(key)
    }

    override suspend fun findBy(
        id: String?, name: String?, description: String?, image: String?, crates: String?
    ): List<Key> {
        return keys.filter {
            (id == null || it.id == id) &&
            (name == null || it.name.contains(name, ignoreCase = true)) &&
            (description == null || it.description.contains(description, ignoreCase = true)) &&
            (image == null || it.image == image) &&
            (crates == null || it.crates.contains(crates))
        }
    }
}
