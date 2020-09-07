package gabrigiunchi.config

import gabrigiunchi.service.cache.CacheService
import gabrigiunchi.service.cache.SelfBalancingCacheService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanProvider(private val selfBalancingCacheService: SelfBalancingCacheService) {

    @Bean
    fun cacheService(): CacheService {
        return this.selfBalancingCacheService
    }
}