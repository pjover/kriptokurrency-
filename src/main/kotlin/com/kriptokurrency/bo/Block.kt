package com.kriptokurrency.bo

import com.kriptokurrency.Constants.Companion.GENESIS_BLOCK

data class Block(
        val timestamp: Long,
        val lastHash: String,
        val hash: String,
        val data: List<Any>
) {
    companion object {
        fun genesis() = GENESIS_BLOCK
    }
}