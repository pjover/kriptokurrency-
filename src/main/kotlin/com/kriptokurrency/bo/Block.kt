package com.kriptokurrency.bo

import com.kriptokurrency.GENESIS_BLOCK
import com.kriptokurrency.cryptoHash

data class Block(
        val timestamp: Long,
        val lastHash: String,
        val hash: String,
        val data: List<Any>
) {
    companion object {

        fun genesis() = GENESIS_BLOCK

        fun mineBlock(lastBlock: Block, data: List<Any>): Block {

            val timestamp = System.currentTimeMillis()
            val lastHash = lastBlock.hash
            return Block(timestamp,
                    lastHash,
                    cryptoHash(listOf(timestamp, lastHash, data)),
                    data)
        }
    }
}