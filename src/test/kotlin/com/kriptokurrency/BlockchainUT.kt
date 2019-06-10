package com.kriptokurrency

import com.kriptokurrency.bo.Block
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.mockk.*
import mu.KLogger

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

            lateinit var blockchain: Blockchain

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

    describe("replaceChain()") {

        val logger = mockk<KLogger>()

        lateinit var blockchain: Blockchain
        lateinit var originalChain: MutableList<Block>
        lateinit var newChain: Blockchain

        fun before1() { // TODO Pending to find the way to automatize

            clearMocks(logger)
            every { logger.error(any<String>()) } just runs
            every { logger.info(any<String>()) } just runs

            blockchain = Blockchain(logger)
            blockchain.addBlock(listOf("fooA", "barA"))
            originalChain = blockchain.chain

            newChain = Blockchain()
            newChain.addBlock(listOf("fooB", "barB"))
        }

        context("when the new chain is not longer") {

            fun before2() { // TODO Pending to find the way to automatize
                before1()
                blockchain.replaceChain(newChain.chain)
            }

            context("when the two chains are equal") {

                before2()

                it("does not replace the chain") {
                    blockchain.chain shouldBe originalChain
                }

                it("logs an error") {
                    verify(exactly = 1) { logger.error(any<String>()) }
                }
            }

            context("when the new chain is shorter") {

                before2()

                it("does not replace the chain") {
                    blockchain.chain shouldBe originalChain
                }

                it("logs an error") {
                    verify(exactly = 1) { logger.error(any<String>()) }

                }
            }

        }

        context("when the new chain is longer") {

            fun before3() { // TODO Pending to find the way to automatize
                before1()
                newChain.addBlock(listOf("foo1", "bar1"))
            }

            context("and the chain is invalid") {

                before3()
                newChain.chain[1] = Block(
                        newChain.chain[1].timestamp,
                        newChain.chain[1].lastHash,
                        newChain.chain[1].hash,
                        listOf("some-bad-and-evil-data"))

                blockchain.replaceChain(newChain.chain)

                it("does not replace the chain") {
                    blockchain.chain shouldBe originalChain
                }

                it("logs an error") {
                    verify(exactly = 1) { logger.error(any<String>()) }
                }
            }

            context("and the chain is valid") {

                before3()
                blockchain.replaceChain(newChain.chain)

                it("replaces the chain") {
                    blockchain.chain shouldBe newChain.chain
                }

                it("logs about the chain replacement") {
                    verify(exactly = 1) { logger.info(any<String>()) }
                }
            }

        }

    }
})