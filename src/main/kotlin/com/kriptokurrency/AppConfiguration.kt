package com.kriptokurrency

import com.kriptokurrency.core.Blockchain
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class AppConfiguration {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kriptokurrency.api"))
                .paths(PathSelectors.ant("/api/*"))
                .build()
                .apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
                "Kripto Kurrency API",
                "Kripto Kurrency API implementation on Kotlin",
                "0.1",
                "Terms of service",
                Contact("Pere Jover", "www.example.com", "myeaddress@company.com"),
                "License of API", "API license URL", emptyList())
    }

    @Bean
    fun blockchain(): Blockchain {
        return Blockchain()
    }
}