package com.kriptokurrency.aux

import org.springframework.stereotype.Component
import java.time.Instant

@Component
class TestTimeManager : TimeManager {

    override val currentInstant: Instant = Instant.from(INSTANT)

    companion object {
        val INSTANT = Instant.ofEpochSecond(1560017716)
    }
}
