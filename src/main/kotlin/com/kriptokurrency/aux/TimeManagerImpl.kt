package com.kriptokurrency.aux

import org.springframework.stereotype.Component
import java.time.Instant

@Component
class TimeManagerImpl : TimeManager {

    override val currentInstant: Instant = Instant.now()
}
