package gabrigiunchi.service.cache

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class FooService {

    fun getTimestamp(): Instant {
        logger.info("Calculating timestamp")
        return Instant.now()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FooService::class.java)
    }
}