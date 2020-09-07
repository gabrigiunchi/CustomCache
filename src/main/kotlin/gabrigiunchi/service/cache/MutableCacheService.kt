package gabrigiunchi.service.cache

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

/**
 * Implementation of a custom cache using an mutable map.
 */
@Service
class MutableCacheService : AbstractCacheService<MutableMap<String, Pair<Instant, Any>>>(mutableMapOf()), CacheService {

    override fun set(key: String, value: Any, ttl: Duration) {
        logger.info("Setting $key")
        this.cache[key] = Instant.now().plus(ttl) to value
    }

    override fun remove(key: String) {
        logger.info("Removing $key")
        this.cache.remove(key)
    }

    override fun removeAll(keys: Set<String>) {
        logger.info("Removing $keys")
        this.cache = this.cache.filter { !keys.contains(it.key) }.toMutableMap()
    }

    override fun clear() {
        logger.info("Clearing cache")
        this.cache.clear()
    }

    override fun cleanup() {
        logger.info("Removing invalid entries")
        this.cache = this.cache.cleanup().toMutableMap()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(CacheService::class.java)
    }
}