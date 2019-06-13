package com.kriptokurrency.core

import kotlin.math.roundToInt


fun averageWork() {

    val blockchain = getHackedGenesisBlockchain()
    val times = mutableListOf<Long>()

    for (i in 0 until 1000) {
        val prevTimestamp = blockchain.chain.last().timestamp

        blockchain.addBlock(listOf("block $i"))
        val nextBlock = blockchain.chain.last()

        val timeDiff = nextBlock.timestamp - prevTimestamp
        times.add(timeDiff)

        val average = times.average().roundToInt()

        println("Time to mine block: $timeDiff ms. Difficulty: ${nextBlock.difficulty}. Average time: $average ms")
    }

}

fun getHackedGenesisBlockchain(): Blockchain {
    val blockchain = Blockchain()

    blockchain.chain[0] = Block(
            System.currentTimeMillis(),
            "-----",
            "hash-one",
            listOf("This is the genesis block"),
            INITIAL_DIFFICULTY,
            0)
    return blockchain
}

fun main() {
    averageWork()
}