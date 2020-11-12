package gabrigiunchi.service.cache

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TimeService {

    fun getTimestamp(): Instant {
        logger.info("Calculating timestamp")
        return Instant.now()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(TimeService::class.java)
    }
}