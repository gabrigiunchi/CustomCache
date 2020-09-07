package gabrigiunchi.controller

import gabrigiunchi.service.cache.CacheService
import gabrigiunchi.service.cache.FooService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/foo")
class FooController(private val fooService: FooService, private val cacheService: CacheService) {

    @GetMapping
    fun getAll(): ResponseEntity<Instant> {
        logger.info("GET all")
        return ResponseEntity.ok(
                this.cacheService.get(TIMESTAMP_KEY) as? Instant ?: run {
                    val t = this.fooService.getTimestamp()
                    this.cacheService.set(TIMESTAMP_KEY, t)
                    t
                }
        )
    }

    companion object {
        private const val TIMESTAMP_KEY = "timestamp"
        private val logger = LoggerFactory.getLogger(FooController::class.java)
    }
}