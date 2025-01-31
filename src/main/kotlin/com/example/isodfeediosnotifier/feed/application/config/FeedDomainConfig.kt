package com.example.isodfeediosnotifier.feed.application.config

import com.example.isodfeediosnotifier.feed.FeedFacade
import com.example.isodfeediosnotifier.feed.core.port.FeedService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class FeedDomainConfig {

    @Bean
    fun restTemplate() : RestTemplate {
        return RestTemplateBuilder().build()
    }

    @Bean
    fun feedService() : FeedService{
        return FeedFacade(RestTemplate())
    }
}