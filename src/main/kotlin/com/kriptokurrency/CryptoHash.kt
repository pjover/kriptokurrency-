package com.kriptokurrency

import org.apache.commons.codec.digest.DigestUtils

fun cryptoHash(texts: List<Any>): String {
    val text = texts.map { it.toString() }.sorted().joinToString(" ")
    return DigestUtils.sha256Hex(text)
}

fun hexToBinary(hexadecimalHash: String): String {

    val binaryHash = StringBuffer()
    for (i in 0 until hexadecimalHash.length) {
        val value = Integer.valueOf(hexadecimalHash[i].toString(), 16)
        val formatted = String.format("%4s", Integer.toBinaryString(value)).replace(" ", "0")
        binaryHash.append(formatted)
    }
    return binaryHash.toString()
}