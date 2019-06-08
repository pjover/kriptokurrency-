package com.kriptokurrency.bo

import com.kriptokurrency.Constants
import com.kriptokurrency.aux.TestTimeManager
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class BlockUT {

    @Nested
    inner class `Block BO tests` {

        @Test
        fun `it should have a timestamp, lastHash, hash and data property`() {

            // Given a timestamp, lastHash, hash and data
            val timestamp = TestTimeManager.INSTANT.toEpochMilli()
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


    @Nested
    inner class `Mine block BO tests` {
        @Test
        fun `mineBlock() sets the lastHash and data correctly`() {

            // Given the last block and the mined data
            val timestamp = TestTimeManager.INSTANT.toEpochMilli()
            val lastBlock = Constants.GENESIS_BLOCK
            val data = listOf("mined data")

            // When we call mineBlock()
            val mineBlock = Block.mineBlock(timestamp, lastBlock, data)

            // Then the data shold be the input data
            mineBlock.timestamp shouldBe timestamp
            mineBlock.lastHash shouldBe lastBlock.hash
            mineBlock.hash shouldBe "SAH-256 hash still not implemented"
            mineBlock.data shouldBe data
        }
    }
}