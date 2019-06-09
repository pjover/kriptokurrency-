package com.kriptokurrency.bo

import com.kriptokurrency.GENESIS_BLOCK
import com.kriptokurrency.cryptoHash
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class BlockUT {

    @Nested
    inner class `Block BO tests` {

        @Test
        fun `it should have a timestamp, lastHash, hash and data property`() {

            // Given a timestamp, lastHash, hash and data
            val timestamp = 1560017716L
            val lastHash = "foo-hash"
            val hash = "bar-hash"
            val data = listOf("blockchain", "data")

            // And a Block
            val block = Block(timestamp, lastHash, hash, data)

            // When we call the properties getters
            // Then it should match the constructor parameters
            block.timestamp shouldBe timestamp
            block.lastHash shouldBe lastHash
            block.hash shouldBe hash
            block.data shouldBe data
        }

        @Test
        fun `genesis() should return the GENESIS_BLOCK`() {

            // Given a genesis block
            val genesisBlock = Block.genesis()

            // Then it should be a Block instance
            genesisBlock shouldBe GENESIS_BLOCK
        }

    }


    @Nested
    inner class `Mine block BO tests` {
        @Test
        fun `mineBlock() sets the lastHash and data correctly`() {

            // Given the last block and the mined data
            val timestamp = System.currentTimeMillis()
            val lastBlock = GENESIS_BLOCK
            val data = listOf("mined data")

            // When we call mineBlock()
            val mineBlock = Block.mineBlock(lastBlock, data)

            // Then the timestamp should be correct
            mineBlock.timestamp shouldBeGreaterThanOrEqual timestamp

            // And the lastHash should be the hash of the last block
            mineBlock.lastHash shouldBe lastBlock.hash

            // And the data shoul be the imput data
            mineBlock.data shouldBe data

            // And creates a SHA-256 hash based on the proper inputs
            mineBlock.hash shouldBe cryptoHash(listOf(mineBlock.timestamp, lastBlock.hash, data))
        }
    }
}