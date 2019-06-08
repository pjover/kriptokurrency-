package com.kriptokurrency.aux

import java.time.Instant

interface TimeManager {

    val currentInstant: Instant
}
