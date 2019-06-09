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
            val sut = Block(timestamp, lastHash, hash, data)

            // When we call the properties getters
            // Then it should match the constructor parameters
            sut.timestamp shouldBe timestamp
            sut.lastHash shouldBe lastHash
            sut.hash shouldBe hash
            sut.data shouldBe data
        }

        @Test
        fun `genesis() should return the GENESIS_BLOCK`() {

            // Given a genesis block
            val sut = Block.genesis()

            // Then it should be a Block instance
            sut shouldBe GENESIS_BLOCK
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
            val sut = Block.mineBlock(lastBlock, data)

            // Then the timestamp should be correct
            sut.timestamp shouldBeGreaterThanOrEqual timestamp

            // And the lastHash should be the hash of the last block
            sut.lastHash shouldBe lastBlock.hash

            // And the data shoul be the imput data
            sut.data shouldBe data

            // And creates a SHA-256 hash based on the proper inputs
            sut.hash shouldBe cryptoHash(listOf(sut.timestamp, lastBlock.hash, data))
        }
    }
}