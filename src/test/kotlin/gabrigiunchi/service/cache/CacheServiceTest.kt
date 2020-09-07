package gabrigiunchi.service.cache

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Duration

abstract class CacheServiceTest(private val cacheService: CacheService) {

    @BeforeEach
    fun cleanup() {
        this.cacheService.clear()
    }

    @Test
    fun `Should set an item`() {
        this.cacheService.set("1", 2)
        Assertions.assertThat(this.cacheService.has("1")).isTrue()
    }

    @Test
    fun `Should get an item`() {
        this.cacheService.set("1", 2)
        this.cacheService.set("3", "a")
        Assertions.assertThat(this.cacheService.get("1")).isEqualTo(2)
        Assertions.assertThat(this.cacheService.get("2")).isNull()
        Assertions.assertThat(this.cacheService.get("3")).isEqualTo("a")
    }

    @Test
    fun `Should get multiple elements`() {
        this.cacheService.set("1", 1)
        this.cacheService.set("2", 2)
        this.cacheService.set("3", 3)
        this.cacheService.set("4", 4)

        Assertions.assertThat(this.cacheService.getAll(setOf("1", "3")))
                .isEqualTo(listOf("1" to 1, "3" to 3))
    }

    @Test
    fun `Should say if it contains an item`() {
        this.cacheService.set("a", 3, Duration.ofHours(2))
        this.cacheService.set("1", 2, Duration.ofDays(-1))
        Assertions.assertThat(this.cacheService.has("a")).isTrue()
        Assertions.assertThat(this.cacheService.has("1")).isFalse()
    }

    @Test
    fun `Should remove an item`() {
        this.cacheService.set("a", 3)
        this.cacheService.set("1", 2)
        Assertions.assertThat(this.cacheService.has("a")).isTrue()
        Assertions.assertThat(this.cacheService.has("1")).isTrue()

        this.cacheService.remove("1")
        Assertions.assertThat(this.cacheService.has("a")).isTrue()
        Assertions.assertThat(this.cacheService.has("1")).isFalse()
    }

    @Test
    fun `Should remove multiple elements`() {
        this.cacheService.set("1", 1)
        this.cacheService.set("2", 2)
        this.cacheService.set("3", 3)
        this.cacheService.set("4", 4)

        this.cacheService.removeAll(setOf("2", "4"))
        Assertions.assertThat(this.cacheService.entries)
                .isEqualTo(setOf("1" to 1, "3" to 3))
    }

    @Test
    fun `Should get the entries of the cache`() {
        this.cacheService.set("a", 3)
        this.cacheService.set("1", 2)
        Assertions.assertThat(this.cacheService.entries)
                .isEqualTo(setOf("a" to 3, "1" to 2))
    }

    @Test
    fun `Should get the entries of the cache ordered by key`() {
        this.cacheService.set("a", 3)
        this.cacheService.set("1", 2)
        this.cacheService.set("3", 2)
        this.cacheService.set("2", 2)
        Assertions.assertThat(this.cacheService.entries)
                .isEqualTo(setOf("a" to 3, "1" to 2, "2" to 2, "3" to 2))
    }

    @Test
    fun `Should clear the cache`() {
        this.cacheService.set("a", 3)
        this.cacheService.set("1", 2)
        Assertions.assertThat(this.cacheService.has("a")).isTrue()
        Assertions.assertThat(this.cacheService.has("1")).isTrue()

        this.cacheService.clear()
        Assertions.assertThat(this.cacheService.has("a")).isFalse()
        Assertions.assertThat(this.cacheService.has("1")).isFalse()
        Assertions.assertThat(this.cacheService.entries).isEmpty()
    }

    @Test
    fun `Should clear the invalid entries`() {
        this.cacheService.set("a", 3)
        this.cacheService.set("1", 2, Duration.ofHours(-1))

        this.cacheService.cleanup()
        Assertions.assertThat(this.cacheService.entries)
                .isEqualTo(setOf("a" to 3))
    }
}