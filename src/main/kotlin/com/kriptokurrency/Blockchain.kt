package com.kriptokurrency

import com.kriptokurrency.bo.Block
import mu.KLogger
import mu.KotlinLogging
import kotlin.math.abs

class Blockchain(
        private val logger: KLogger = KotlinLogging.logger {}
) {

    var chain = mutableListOf(Block.genesis())

    companion object {

        fun isValid(blockchain: Blockchain): Boolean {

            return isValidChain(blockchain.chain)
        }

        private fun isValidChain(chain: MutableList<Block>): Boolean {
            if (firstBlockIsNotGenesis(chain)) return false
            if (!allBlocksAreChained(chain)) return false
            if (isThereAnyDifficultyJump(chain)) return false
            return allBlocksAreValid(chain)
        }

        private fun firstBlockIsNotGenesis(chain: MutableList<Block>) = chain.first() != Block.genesis()

        private fun allBlocksAreChained(chain: MutableList<Block>): Boolean {

            for (i in 1 until chain.size) {
                if (chain[i].lastHash != chain[i - 1].hash) return false
            }
            return true
        }

        private fun allBlocksAreValid(chain: MutableList<Block>) = chain.filter { isInvalidBlock(it) }.isEmpty()

        private fun isInvalidBlock(block: Block): Boolean {

            return if (block == GENESIS_BLOCK) false
            else cryptoHash(listOf(block.timestamp, block.nonce, block.difficulty, block.lastHash, block.data)) != block.hash
        }

        private fun isThereAnyDifficultyJump(chain: MutableList<Block>): Boolean {

            for (i in 1 until chain.size) {
                val jump = abs(chain[i].difficulty - chain[i - 1].difficulty)
                if (jump > 1) return true
            }
            return false
        }
    }

    fun addBlock(data: List<Any>) {

        val block = Block.mineBlock(chain.last(), data)
        chain.add(block)
    }

    fun replaceChain(newChain: MutableList<Block>) {
        if (cannotReplaceChain(newChain)) return
        this.chain = newChain
    }

    private fun cannotReplaceChain(newChain: MutableList<Block>): Boolean {
        if (this.chain.size >= newChain.size) {
            logger.error("The incoming chain must be longer")
            return true
        } else if (!isValidChain(newChain)) {
            logger.error("The incoming chain must be valid")
            return true
        }
        logger.info("Replacing chain with $chain")
        return false
    }
}