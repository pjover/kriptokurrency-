package com.kriptokurrency.core

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

            var difficulty: Int
            var timestamp: Long
            var nonce = 0L
            var hash: String
            do {
                nonce++
                timestamp = System.currentTimeMillis()
                difficulty = adjustDifficulty(lastBlock, timestamp)
                hash = cryptoHash(listOf(timestamp, lastHash, data, difficulty, nonce))
            } while (doesNotMatchDifficulty(hash, difficulty))

            return Block(timestamp, lastHash, hash, data, difficulty, nonce)
        }

        private fun doesNotMatchDifficulty(hash: String, difficulty: Int) = !hexToBinary(hash).startsWith("0".repeat(difficulty))

        fun adjustDifficulty(originalBlock: Block, timestamp: Long): Int {

            return when {
                originalBlock.difficulty < 1 -> 1
                timestamp - originalBlock.timestamp > MINE_RATE -> originalBlock.difficulty - 1
                else -> originalBlock.difficulty + 1
            }
        }

    }
}