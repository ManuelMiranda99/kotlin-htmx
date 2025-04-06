package stsa.kotlin_htmx.util

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import java.security.MessageDigest
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

object CacheManager {
    private val caches: ConcurrentHashMap<String, Cache<String, Any>> = ConcurrentHashMap()

    fun getCache(name: String): Cache<String, Any> {
        return caches.computeIfAbsent(name) {
            Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build()
        }
    }
}

fun generateCacheKey(vararg parts: String?): String {
    val combined = parts.joinToString(separator = "|") { it ?: "" }
    return MessageDigest.getInstance("SHA-256")
        .digest(combined.toByteArray())
        .joinToString("") { "%02x".format(it) }
}

suspend inline fun <reified T : Any> cacheOrGet(
    cacheName: String,
    cacheKey: String,
    crossinline fetch: suspend () -> T
): T {
    val cache = CacheManager.getCache(cacheName)

    val cached = cache.getIfPresent(cacheKey) as? T
    return if (cached != null) {
        cached
    } else {
        val fresh = fetch()
        cache.put(cacheKey, fresh)
        fresh
    }
}
