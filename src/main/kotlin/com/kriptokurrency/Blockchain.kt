package com.kriptokurrency

import com.kriptokurrency.bo.Block
import mu.KLogger
import mu.KotlinLogging

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

        private fun isInvalidBlock(block: Block) = cryptoHash(listOf(block.timestamp, block.lastHash, block.data)) != block.hash
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