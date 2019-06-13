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

    describe("hexToBinary()") {

        it("converts an hexadecimal SHA-256 hash into its binary representation") {
            val sha256 = "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae"
            val expected = "0010110000100110101101000110101101101000111111111100011010001111111110011001101101000101001111000001110100110000010000010011010000010011010000100010110101110000011001001000001110111111101000001111100110001010010111101000100001100010011001101110011110101110"

            hexToBinary(sha256) shouldBe expected
        }
    }
})