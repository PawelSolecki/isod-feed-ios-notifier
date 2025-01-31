package com.example.isodfeediosnotifier.feed.application.config

import com.example.isodfeediosnotifier.feed.FeedFacade
import com.example.isodfeediosnotifier.feed.core.port.FeedService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.Charset

@Configuration
class FeedDomainConfig {


    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .messageConverters(
                StringHttpMessageConverter(Charset.forName("UTF-8")), // Obsługa text/plain
                MappingJackson2HttpMessageConverter() // JSON
            )
            .build()
    }

    @Bean
    fun feedService(restTemplate: RestTemplate): FeedService {
        return FeedFacade(restTemplate)
    }

}