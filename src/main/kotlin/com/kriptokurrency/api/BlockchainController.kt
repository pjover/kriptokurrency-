package com.kriptokurrency.api

import com.kriptokurrency.core.Block
import com.kriptokurrency.core.Blockchain
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.*


@Api(tags = ["Blockchain"], description = "Blockchain operations")
@RestController
@RequestMapping("/api")
class BlockchainController(
        private val blockchain: Blockchain
) {

    @ApiOperation(value = "Returns the blockchain")
    @GetMapping("/blocks")
    fun blocks(): List<Block> {
        return blockchain.chain
    }

    @ApiOperation(value = "Adds a new block to the blockchain", consumes = "application/json")
    @PostMapping("/mine", consumes = ["application/json"])
    fun mine(
            @ApiParam(name = "data", required = true)
            @RequestBody
            data: List<String>
    ): List<Block> {

        blockchain.addBlock(data)
        return blockchain.chain
    }

}