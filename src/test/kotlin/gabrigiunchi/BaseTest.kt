package gabrigiunchi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(classes = [CustomCacheApplication::class])
@AutoConfigureMockMvc
abstract class BaseTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc
}