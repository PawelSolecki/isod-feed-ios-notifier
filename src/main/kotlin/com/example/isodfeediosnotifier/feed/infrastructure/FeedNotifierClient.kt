package com.example.isodfeediosnotifier.feed.infrastructure

import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate

@Slf4j
class FeedNotifierClient(
    private val restTemplate: RestTemplate
) {

    @Value("\${pushcut.api-key}")
    lateinit var pushcutApiKey: String

    @Value("\${pushcut.url}")
    lateinit var pushcutUrl: String

    @Value("\${custom-notification}")
    lateinit var customNotification: String

    fun sendNotification(requestBody: Map<String, String>) {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("API-Key", pushcutApiKey)
        }
        val isCustomNotification = customNotification.toBoolean()
        val requestEntity = if (isCustomNotification) {
            HttpEntity(requestBody, headers)
        } else {
            HttpEntity(null, headers)
        }

        restTemplate.exchange(pushcutUrl, HttpMethod.POST, requestEntity, String::class.java)

    }
}