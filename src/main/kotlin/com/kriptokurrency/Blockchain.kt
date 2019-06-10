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
            if (chain.first() != Block.genesis()) return false
            return chain.filter { isInvalidBlock(it) }.isEmpty()
        }

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