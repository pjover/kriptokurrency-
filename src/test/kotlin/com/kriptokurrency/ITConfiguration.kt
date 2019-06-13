package com.kriptokurrency

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("test")
@Configuration
@ComponentScan(basePackages = ["com.kriptokurrency"])
@EnableAutoConfiguration()
class ITConfiguration

