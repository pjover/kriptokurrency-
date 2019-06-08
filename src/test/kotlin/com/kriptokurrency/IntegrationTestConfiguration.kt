package com.kriptokurrency

import com.kriptokurrency.aux.TestTimeManager
import com.kriptokurrency.aux.TimeManager
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("test")
@Configuration
@ComponentScan(basePackages = ["com.kriptokurrency"])
@EnableAutoConfiguration()
class IntegrationTestConfiguration {

    @Bean
    fun timeManager(): TimeManager {
        return TestTimeManager()
    }
}

