package com.kriptokurrency.bo

import com.kriptokurrency.GENESIS_BLOCK
import com.kriptokurrency.MINE_RATE
import com.kriptokurrency.cryptoHash
import com.kriptokurrency.hexToBinary
import io.kotlintest.inspectors.forOne
import io.kotlintest.matchers.numerics.shouldBeGreaterThanOrEqual
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class BlockUT : DescribeSpec() {

    init {

        describe("Block") {
            val timestamp = 1560017716L
            val lastHash = "foo-hash"
            val hash = "bar-hash"
            val data = listOf("blockchain", "data")
            val difficulty = 1
            val nonce = 1L
            val block = Block(timestamp, lastHash, hash, data, difficulty, nonce)

            it("has a timestamp, lastHash, hash, and data property") {
                block.timestamp shouldBe timestamp
                block.lastHash shouldBe lastHash
                block.hash shouldBe hash
                block.data shouldBe data
                block.nonce shouldBe nonce
                block.difficulty shouldBe difficulty
            }
        }

        describe("genesis()") {
            val genesisBlock = Block.genesis()

            it("returns the genesis data") {
                genesisBlock shouldBe GENESIS_BLOCK
            }
        }

        describe("mineBlock()") {
            val timestamp = System.currentTimeMillis()
            val lastBlock = Block.genesis()
            val data = listOf("mined data")
            val minedBlock = Block.mineBlock(lastBlock, data)

            it("sets the lastHash to be the hash of the lastBlock") {
                minedBlock.lastHash shouldBe lastBlock.hash
            }

            it("sets the data") {
                minedBlock.data shouldBe data
            }

            it("sets a timestamp") {
                minedBlock.timestamp shouldBeGreaterThanOrEqual timestamp
            }

            it("creates a SHA-256 `hash` based on the proper inputs") {
                minedBlock.hash shouldBe
                        cryptoHash(listOf(
                                minedBlock.timestamp,
                                minedBlock.nonce,
                                minedBlock.difficulty,
                                lastBlock.hash,
                                data)
                        )
            }

            it("sets a hash that matches the difficulty criteria") {
                hexToBinary(minedBlock.hash).startsWith("0".repeat(minedBlock.difficulty)) shouldBe true
            }

            it("adjust the difficulty") {
                val possibleResults = listOf(lastBlock.difficulty - 1, lastBlock.difficulty + 1)
                possibleResults.forOne { it shouldBe minedBlock.difficulty }
            }
        }

        describe("adjustDifficulty()") {
            val timestamp = 2000L
            val lastHash = "foo-hash"
            val hash = "bar-hash"
            val data = listOf("blockchain", "data")
            val difficulty = 1
            val nonce = 1L
            val block = Block(timestamp, lastHash, hash, data, difficulty, nonce)

            it("raises the difficulty for a quickly mined block") {
                Block.adjustDifficulty(
                        block,
                        block.timestamp + MINE_RATE - 100
                ) shouldBe block.difficulty + 1
            }

            it("lowers the difficulty for a slowly mined block") {
                Block.adjustDifficulty(
                        block,
                        block.timestamp + MINE_RATE + 100
                ) shouldBe block.difficulty - 1
            }

            it("has a lower limit of 1") {
                val block2 = Block(timestamp, lastHash, hash, data, -1, nonce)
                Block.adjustDifficulty(block2, timestamp) shouldBe 1
            }
        }
    }
}