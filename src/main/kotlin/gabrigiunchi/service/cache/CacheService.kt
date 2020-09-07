package gabrigiunchi.service.cache

import java.time.Duration

interface CacheService {
    fun has(key: String): Boolean
    fun get(key: String): Any?
    fun getAll(keys: Set<String>): List<Pair<String, Any>>
    fun set(key: String, value: Any, ttl: Duration = DEFAULT_TTL)
    fun remove(key: String)
    fun removeAll(keys: Set<String>)
    fun clear()
    fun cleanup()
    val entries: Set<Pair<String, Any>>

    companion object {
        val DEFAULT_TTL: Duration = Duration.ofHours(12)
    }
}