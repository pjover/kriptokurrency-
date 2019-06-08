package com.kriptokurrency.bo

import com.kriptokurrency.GENESIS_BLOCK

data class Block(
        val timestamp: Long,
        val lastHash: String,
        val hash: String,
        val data: List<Any>
) {
    companion object {
        fun genesis() = GENESIS_BLOCK
        fun mineBlock(lastBlock: Block, data: List<Any>): Block {
            return Block(
                    System.currentTimeMillis(),
                    lastBlock.hash,
                    computeHash(),
                    data)
        }

        fun computeHash() = "SAH-256 hash still not implemented"
    }
}