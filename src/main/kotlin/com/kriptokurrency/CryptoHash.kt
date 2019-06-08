package com.kriptokurrency

import org.apache.commons.codec.digest.DigestUtils

fun cryptoHash(texts: List<String>): String {
    val text = texts.sorted().joinToString(" ")
    return DigestUtils.sha256Hex(text)
}