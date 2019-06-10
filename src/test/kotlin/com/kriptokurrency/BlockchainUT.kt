package com.kriptokurrency

import com.kriptokurrency.bo.Block
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

class BlockchainUT : DescribeSpec({

    describe("Blockchain") {

        val blockchain = Blockchain()

        it("starts with the genesis block") {
            blockchain.chain[0] shouldBe Block.genesis()
        }

        it("adds a new block to the chain") {
            val data = listOf("foo bar")
            blockchain.addBlock(data)

            blockchain.chain.last().data shouldBe data
        }
    }

    describe("isValidChain()") {

        context("when the chain does not start with the genesis block") {

            val blockchain = Blockchain()
            blockchain.chain[0] = Block(
                    System.currentTimeMillis(),
                    "---",
                    "xxx",
                    listOf("foo", "bar"))

            it("returns false") {
                Blockchain.isValid(blockchain) shouldBe false
            }
        }

        context("when the chain starts with the genesis block and has multiple blocks") {

            var blockchain = Blockchain()
            fun before() { // TODO Pending to find the way to automatize
                blockchain = Blockchain()
                blockchain.addBlock(listOf("foo1", "bar1"))
                blockchain.addBlock(listOf("foo2", "bar2"))
                blockchain.addBlock(listOf("foo3", "bar3"))
            }

            context("and a lastHash reference has changed returns false") {
                before()

                blockchain.chain[2] = Block(
                        blockchain.chain[2].timestamp,
                        "broken-lastHash",
                        blockchain.chain[2].hash,
                        blockchain.chain[2].data)
                it("returns false") {
                    Blockchain.isValid(blockchain) shouldBe false
                }
            }

            context("and the chain contains a block with an invalid field") {
                before()

                blockchain.chain[2] = Block(
                        blockchain.chain[2].timestamp,
                        blockchain.chain[2].lastHash,
                        blockchain.chain[2].hash,
                        listOf("some-bad-and-evil-data"))
                it("returns false") {
                    Blockchain.isValid(blockchain) shouldBe false
                }
            }

            context("and the chain does not contain any invalid blocks") {
                before()

                it("returns true") {
                    Blockchain.isValid(blockchain) shouldBe true
                }
            }
        }

    }
})