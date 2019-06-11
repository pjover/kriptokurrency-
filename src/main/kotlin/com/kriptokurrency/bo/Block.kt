package com.kriptokurrency.bo

import com.kriptokurrency.GENESIS_BLOCK
import com.kriptokurrency.cryptoHash

data class Block(
        val timestamp: Long,
        val lastHash: String,
        val hash: String,
        val data: List<Any>,
        val difficulty: Int,
        val nonce: Long
) {
    companion object {

        fun genesis() = GENESIS_BLOCK

        fun mineBlock(lastBlock: Block, data: List<Any>): Block {

            val lastHash = lastBlock.hash
            val difficulty = lastBlock.difficulty
            val prefix = "0".repeat(difficulty)

            var timestamp: Long
            var nonce = 0L
            var hash: String
            do {
                nonce++
                timestamp = System.currentTimeMillis()
                hash = cryptoHash(listOf(timestamp, lastHash, data, difficulty, nonce))
            } while (!hash.startsWith(prefix))

            return Block(timestamp, lastHash, hash, data, difficulty, nonce)
        }
    }
}