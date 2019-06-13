package com.kriptokurrency.api

import com.kriptokurrency.core.Block
import com.kriptokurrency.core.Blockchain
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Api(tags = ["Blockchain"], description = "Blockchain operations")
@RestController
@RequestMapping("/api")
class BlockchainController(
        private val blockchain: Blockchain
) {

    @ApiOperation(value = "Retrieves the blockchain")
    @GetMapping("/blocks")
    fun blocks(): List<Block> {
        return blockchain.chain
    }
}