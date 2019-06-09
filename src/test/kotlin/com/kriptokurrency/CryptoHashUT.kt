package com.kriptokurrency

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CryptoHashUT {

    @Nested
    inner class `CryptoHash tests` {

        @Test
        fun `cryptoHash() generate a SHA-256 hashed output`() {

            // Given a text to hash
            val texts = listOf("foo")

            // When we call to hash()
            val actual = cryptoHash(texts)

            // Then we should get the SHA-256 hash
            actual shouldBe "2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae"
        }

        @Test
        fun `cryptoHash() produces the same hash with the same inputs arguments in any order`() {

            // Given some texts to hash
            val texts1 = listOf(1, "two", "three")
            val texts2 = listOf("three", 1, "two")

            // When we call to hash()
            val actual1 = cryptoHash(texts1)
            val actual2 = cryptoHash(texts2)

            // Then we should get the same hash
            actual1 shouldBe actual2
        }
    }
}