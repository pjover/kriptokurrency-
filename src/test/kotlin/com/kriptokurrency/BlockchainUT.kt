package com.kriptokurrency

import com.kriptokurrency.bo.Block
import io.kotlintest.shouldBe
import org.junit.jupiter.api.BeforeEach
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

    @Nested
    inner class `Validate blockchain tests` {

        // Given a Blockchain that has multiple blocks
        lateinit var sut: Blockchain

        @BeforeEach
        fun clear() {
            sut = Blockchain()
            sut.addBlock(listOf("foo", "bar"))
            sut.addBlock(listOf("Bears"))
            sut.addBlock(listOf("Bears"))
            sut.addBlock(listOf("Battleship Galactica"))
        }

        @Test
        fun `A chain that does not start with the genesis block`() {

            // And does not start with the genesis block
            sut.chain[0] = Block(
                    System.currentTimeMillis(),
                    "---",
                    "xxx",
                    listOf("foo", "bar"))

            // When we validate the chain
            // Then it should return false
            Blockchain.isValid(sut) shouldBe false
        }

        @Test
        fun `A chain that contains a block that the lastHash reference has changed`() {

            // And has a block with a changed lastHash reference
            sut.chain[2] = Block(
                    sut.chain[2].timestamp,
                    "broken-lastHash",
                    sut.chain[2].hash,
                    sut.chain[2].data)

            // When we validate the chain
            // Then it should return false
            Blockchain.isValid(sut) shouldBe false
        }

        @Test
        fun `A chain that contains a block with an invalid field`() {

            // And has a block with a changed data
            sut.chain[2] = Block(
                    sut.chain[2].timestamp,
                    sut.chain[2].lastHash,
                    sut.chain[2].hash,
                    listOf("some-bad-and-evil-data"))

            // When we validate the chain
            // Then it should return false
            Blockchain.isValid(sut) shouldBe false
        }


        @Test
        fun `A valid chain does not contain any invalid blocks`() {

            // When we validate the chain
            // Then it should return true
            Blockchain.isValid(sut) shouldBe true
        }

    }
}