package com.kriptokurrency

import com.kriptokurrency.bo.Block
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class BlockchainUT {

    @Nested
    inner class `Blockchain tests` {

        // Given a Blockchain
        val sut = Blockchain()

        @Test
        fun `Blockchain should start with the genesis block`() {

            // When we get the first block
            val actual = sut.chain[0]

            // Then it should be the genesis block
            actual shouldBe Block.genesis()
        }

        @Test
        fun `add() adds a new block to the chain`() {

            // When we add a new block
            val data = listOf("foo bar")
            sut.addBlock(data)

            // Then it should be the genesis block
            sut.chain.last().data shouldBe data
        }
    }
}