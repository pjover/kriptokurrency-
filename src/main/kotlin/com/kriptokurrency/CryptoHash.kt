package com.kriptokurrency

import org.apache.commons.codec.digest.DigestUtils

fun cryptoHash(texts: List<Any>): String {
    val text = texts.map { it.toString() }.sorted().joinToString(" ")
    return DigestUtils.sha256Hex(text)
}