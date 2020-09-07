package gabrigiunchi.service.cache

import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.Instant

/**
 * Implementation of a custom cache using an immutable map.
 */
class ImmutableCacheService : AbstractCacheService<Map<String, Pair<Instant, Any>>>(emptyMap()), CacheService {

    override fun set(key: String, value: Any, ttl: Duration) {
        logger.info("Setting $key")
        this.cache = this.cache.plus(key to (Instant.now().plus(ttl) to value))
    }

    override fun remove(key: String) {
        logger.info("Removing $key")
        this.cache = this.cache.minus(key)
    }

    override fun removeAll(keys: Set<String>) {
        logger.info("Removing $keys")
        this.cache = this.cache.minus(keys)
    }

    override fun clear() {
        logger.info("Clearing cache")
        this.cache = emptyMap()
    }

    override fun cleanup() {
        logger.info("Removing invalid entries")
        this.cache = this.cache.cleanup()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ImmutableCacheService::class.java)
    }
}
