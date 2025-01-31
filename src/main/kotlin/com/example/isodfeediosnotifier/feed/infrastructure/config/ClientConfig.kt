package com.example.isodfeediosnotifier.feed.infrastructure.config

import com.example.isodfeediosnotifier.feed.infrastructure.FeedHttpClient
import com.example.isodfeediosnotifier.feed.infrastructure.FeedNotifierClient
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.Charset

@Configuration
class ClientConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .messageConverters(
                StringHttpMessageConverter(Charset.forName("UTF-8")),
                MappingJackson2HttpMessageConverter()
            )
            .build()
    }

    @Bean
    fun feedHttpClient(restTemplate: RestTemplate): FeedHttpClient {
        return FeedHttpClient(restTemplate)
    }

    @Bean
    fun feedNotifierClient(restTemplate: RestTemplate): FeedNotifierClient {
        return FeedNotifierClient(restTemplate)
    }
}