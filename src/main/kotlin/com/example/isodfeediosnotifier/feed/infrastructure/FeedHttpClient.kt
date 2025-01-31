package com.example.isodfeediosnotifier.feed.infrastructure

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.client.RestTemplate

class FeedHttpClient(
    private val restTemplate: RestTemplate
) {
    @Value("\${isod.url}")
    private lateinit var isodUrl: String
    fun getFeedList(): String? {
        return restTemplate.getForObject(isodUrl, String::class.java)
    }
}