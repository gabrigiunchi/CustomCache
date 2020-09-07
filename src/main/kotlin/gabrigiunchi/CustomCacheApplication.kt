package gabrigiunchi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CustomCacheApplication

fun main(args: Array<String>) {
	runApplication<CustomCacheApplication>(*args)
}
