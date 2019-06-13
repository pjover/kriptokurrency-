package com.kriptokurrency.core

import java.time.Instant

const val MINE_RATE = 1000

const val INITIAL_DIFFICULTY = 3

val GENESIS_BLOCK = Block(
        Instant.EPOCH.toEpochMilli(),
        "-----",
        "hash-one",
        listOf("This is the genesis block"),
        INITIAL_DIFFICULTY,
        0)
