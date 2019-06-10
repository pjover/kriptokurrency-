package com.kriptokurrency

import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec

internal class CryptoHashUT : DescribeSpec({

    describe("cryptoHash()") {

        it("generates a SHA-256 hashed output") {
            cryptoHash(listOf("foo")) shouldBe "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae"
        }

        it("produces the same hash with the same input arguments in any order") {
            cryptoHash(listOf(1, "two", "three")) shouldBe cryptoHash(listOf("three", 1, "two"))
        }
    }
})