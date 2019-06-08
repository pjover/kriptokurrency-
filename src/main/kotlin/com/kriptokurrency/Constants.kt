package com.kriptokurrency

import com.kriptokurrency.bo.Block
import java.time.Instant

class Constants {
    companion object {
        var GENESIS_BLOCK = Block(
                Instant.EPOCH.toEpochMilli(),
                "-----",
                "hash-one",
                emptyList())

    }
}