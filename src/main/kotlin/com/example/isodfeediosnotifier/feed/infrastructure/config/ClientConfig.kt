package com.example.isodfeediosnotifier.feed.infrastructure.config

import com.example.isodfeediosnotifier.feed.infrastructure.FeedHttpClient
import com.example.isodfeediosnotifier.feed.infrastructure.FeedNotifierClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ClientConfig {
    @Bean
    fun feedHttpClient(): FeedHttpClient {
        return FeedHttpClient(RestTemplate())
    }

    @Bean
    fun feedNotifierClient(): FeedNotifierClient {
        return FeedNotifierClient(RestTemplate())
    }
}