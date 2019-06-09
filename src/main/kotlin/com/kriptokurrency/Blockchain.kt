package com.kriptokurrency

import com.kriptokurrency.bo.Block

class Blockchain() {

    var chain = mutableListOf(Block.genesis())

    fun addBlock(data: List<Any>) {

        val block = Block.mineBlock(chain.last(), data)
        chain.add(block)
    }

    companion object {

        fun isValid(blockchain: Blockchain): Boolean {

            if (blockchain.chain.first() != Block.genesis()) return false
            return blockchain.chain.filter { isInvalid(it) }.isEmpty()
        }

        private fun isInvalid(block: Block) = cryptoHash(listOf(block.timestamp, block.lastHash, block.data)) != block.hash
    }
}