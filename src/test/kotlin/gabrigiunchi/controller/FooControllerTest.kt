package gabrigiunchi.controller

import gabrigiunchi.BaseTest
import gabrigiunchi.service.cache.CacheService
import gabrigiunchi.service.cache.FooService
import gabrigiunchi.service.cache.SelfBalancingCacheService
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.Duration
import java.time.Instant

@ExtendWith(MockKExtension::class)
class FooControllerTest : BaseTest() {

    @SpyBean
    private lateinit var cacheService: SelfBalancingCacheService

    @SpyBean
    private lateinit var fooService: FooService

    @Test
    fun `Should use the cache`() {
        val t = Instant.now()
        Mockito.doReturn(t).`when`(fooService).getTimestamp()
        this.mockMvc.perform(MockMvcRequestBuilders.get("/foo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)

        this.mockMvc.perform(MockMvcRequestBuilders.get("/foo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)

        Mockito.verify(cacheService, Mockito.times(2)).get("timestamp")
        Mockito.verify(fooService, Mockito.times(1)).getTimestamp()
        Mockito.verify(cacheService, Mockito.times(1)).set("timestamp", t, Duration.ofHours(12))
    }
}