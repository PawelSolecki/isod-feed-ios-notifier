package com.example.isodfeediosnotifier.feed.application.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeedDomainConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply { registerModule(KotlinModule.Builder().build()) }
    }


}