package com.kriptokurrency.core

import io.kotlintest.IsolationMode
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.mockk.*
import mu.KLogger

class BlockchainUT : DescribeSpec() {

    override fun isolationMode() = IsolationMode.InstancePerLeaf

    init {
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
                        listOf("foo", "bar"),
                        1,
                        1)

                it("returns false") {
                    Blockchain.isValid(blockchain) shouldBe false
                }
            }

            context("when the chain starts with the genesis block and has multiple blocks") {
                val blockchain = Blockchain()
                blockchain.addBlock(listOf("foo1", "bar1"))
                blockchain.addBlock(listOf("foo2", "bar2"))
                blockchain.addBlock(listOf("foo3", "bar3"))

                context("and a lastHash reference has changed returns false") {
                    blockchain.chain[2] = Block(
                            blockchain.chain[2].timestamp,
                            "broken-lastHash",
                            blockchain.chain[2].hash,
                            blockchain.chain[2].data,
                            1,
                            1)

                    it("returns false") {
                        Blockchain.isValid(blockchain) shouldBe false
                    }
                }

                context("and the chain contains a block with an invalid field") {
                    blockchain.chain[2] = Block(
                            blockchain.chain[2].timestamp,
                            blockchain.chain[2].lastHash,
                            blockchain.chain[2].hash,
                            listOf("some-bad-and-evil-data"),
                            1,
                            1)

                    it("returns false") {
                        Blockchain.isValid(blockchain) shouldBe false
                    }
                }

                context("and a chain contains a block with a jumped difficulty") {
                    val lastBlock = blockchain.chain.last()
                    val lastHash = lastBlock.hash
                    val timestamp = System.currentTimeMillis()
                    val nonce = 0L
                    val data = emptyList<String>()
                    val difficulty = lastBlock.difficulty - 3
                    val hash = cryptoHash(listOf(timestamp, lastHash, data, difficulty, nonce))
                    val badBlock = Block(
                            timestamp, lastHash, hash, data, difficulty, nonce
                    )
                    blockchain.chain.add(badBlock)

                    it("returns false") {
                        Blockchain.isValid(blockchain) shouldBe false
                    }
                }

                context("and the chain does not contain any invalid blocks") {

                    it("returns true") {
                        Blockchain.isValid(blockchain) shouldBe true
                    }
                }
            }
        }

        describe("replaceChain()") {
            val logger = mockk<KLogger>()
            every { logger.error(any<String>()) } just runs
            every { logger.info(any<String>()) } just runs

            val blockchain = Blockchain(logger)
            blockchain.addBlock(listOf("fooA", "barA"))
            val originalChain = blockchain.chain

            val newChain = Blockchain()
            newChain.addBlock(listOf("fooB", "barB"))

            context("when the new chain is not longer") {
                blockchain.replaceChain(newChain.chain)

                context("when the two chains are equal") {

                    it("does not replace the chain") {
                        blockchain.chain shouldBe originalChain
                    }

                    it("logs an error") {
                        verify(exactly = 1) { logger.error(any<String>()) }
                    }
                }

                context("when the new chain is shorter") {

                    it("does not replace the chain") {
                        blockchain.chain shouldBe originalChain
                    }

                    it("logs an error") {
                        verify(exactly = 1) { logger.error(any<String>()) }
                    }
                }

            }

            context("when the new chain is longer") {
                newChain.addBlock(listOf("foo1", "bar1"))

                context("and the chain is invalid") {
                    newChain.chain[1] = Block(
                            newChain.chain[1].timestamp,
                            newChain.chain[1].lastHash,
                            newChain.chain[1].hash,
                            listOf("some-bad-and-evil-data"),
                            1,
                            1)
                    blockchain.replaceChain(newChain.chain)

                    it("does not replace the chain") {
                        blockchain.chain shouldBe originalChain
                    }

                    it("logs an error") {
                        verify(exactly = 1) { logger.error(any<String>()) }
                    }
                }

                context("and the chain is valid") {
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
    }
}