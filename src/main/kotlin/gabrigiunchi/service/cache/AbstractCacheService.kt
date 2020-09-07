package gabrigiunchi.service.cache

import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.Instant

/**
 * Abstract class which implements some functionalities of a cache, such as get and contains
 *
 * Set and remove functions are delegated to the child classes
 *
 * The key for every entry is String and the content can be of any type.
 *
 * The casting to the real class is delegated to the caller of this service.
 */
abstract class AbstractCacheService<T : Map<String, Pair<Instant, Any>>>(protected var cache: T) : CacheService {

    override fun has(key: String): Boolean {
        logger.info("Checking if cache contains $key")
        return this.cache.containsKey(key) && this.isValid(this.cache[key]!!.first)
    }

    override fun get(key: String): Any? {
        return if (this.has(key)) {
            logger.info("Returning $key")
            this.cache[key]!!.second
        } else {
            logger.info("Cache does not contain $key")
            null
        }
    }

    override fun getAll(keys: Set<String>): List<Pair<String, Any>> {
        logger.info("Returning $keys")
        return this.cache.filter { keys.contains(it.key) }.map { it.key to it.value.second }
    }

    abstract override fun set(key: String, value: Any, ttl: Duration)
    abstract override fun remove(key: String)
    abstract override fun removeAll(keys: Set<String>)
    abstract override fun clear()
    abstract override fun cleanup()

    override val entries: Set<Pair<String, Any>>
        get() {
            logger.info("Returning all valid entries")
            return this.cache
                    .filter { this.isValid(it.value.first) }
                    .map { it.key to it.value.second }
                    .toSet()
        }

    private fun isValid(expireDate: Instant): Boolean = expireDate >= Instant.now()
    protected fun Map<String, Pair<Instant, Any>>.cleanup(): Map<String, Pair<Instant, Any>> = this.filter { isValid(it.value.first) }

    companion object {
        private val logger = LoggerFactory.getLogger(AbstractCacheService::class.java)
    }
}