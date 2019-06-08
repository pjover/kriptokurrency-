package com.kriptokurrency

import com.kriptokurrency.bo.Block
import java.time.Instant

val GENESIS_BLOCK = Block(
        Instant.EPOCH.toEpochMilli(),
        "-----",
        "hash-one",
        emptyList())
