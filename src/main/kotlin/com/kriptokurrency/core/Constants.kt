package com.kriptokurrency.core

const val MINE_RATE = 1000

const val INITIAL_DIFFICULTY = 3

val GENESIS_BLOCK = Block(
        1560511686169,
        "-----",
        "hash-one",
        listOf("The genesis block"),
        INITIAL_DIFFICULTY,
        0)
