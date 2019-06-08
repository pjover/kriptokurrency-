package com.kriptokurrency.bo

import com.kriptokurrency.Constants
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class BlockUT {

    @Nested
    inner class `Block BO tests` {

        @Test
        fun `it should have a timestamp, lastHash, hash and data property`() {

            // Given a timestamp, lastHash, hash and data
            val timestamp = 123L
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
            genesisBlock shouldBe Constants.GENESIS_BLOCK
        }
    }
}