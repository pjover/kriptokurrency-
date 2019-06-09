package com.kriptokurrency

import com.kriptokurrency.bo.Block

class Blockchain() {

    var chain = mutableListOf(Block.genesis())

    fun addBlock(data: List<Any>) {
        val block = Block.mineBlock(chain.last(), data)
        chain.add(block)
    }
}