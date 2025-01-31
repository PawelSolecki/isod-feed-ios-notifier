package com.example.isodfeediosnotifier.feed

import com.example.isodfeediosnotifier.feed.core.model.FeedDTO
import com.example.isodfeediosnotifier.feed.core.model.FeedResponseDTO
import com.example.isodfeediosnotifier.feed.core.port.FeedService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class FeedFacade(private val restTemplate: RestTemplate) : FeedService {


    @Value("\${pushcut.api-key}")
    lateinit var pushcutApiKey: String

    @Value("\${pushcut.url}")
    lateinit var pushcutUrl: String

    @Value("\${isod.url}")
    lateinit var isodUrl: String


    override fun startChecking() {
        Thread {
            while (true) {
                try {
                    val feed = checkForNewFeed()

                    if (feed != null) {
                        println("[!] New announcement: ${feed.subject}")
                        sendNotification()
                    }

                    TimeUnit.SECONDS.sleep(15)

                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    println("Error while fetching announcement: ${e.message}")
                }
            }
        }.start()
    }

    private var lastProcessedFeed: FeedDTO? = null

    private fun checkForNewFeed(): FeedDTO? {
        return try {
            val response = restTemplate.getForObject(isodUrl, String::class.java)

            response?.let { json ->
                val objectMapper = ObjectMapper().apply { registerModule(KotlinModule.Builder().build()) }
                val feedResponse = objectMapper.readValue(json, FeedResponseDTO::class.java)

                val latestFeed = feedResponse.items.first()

                if (latestFeed != null && latestFeed != lastProcessedFeed) {
                    lastProcessedFeed = latestFeed
                    latestFeed
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            println("Error while fetching announcement: ${e.message}")
            null
        }
    }

    override fun sendNotification() {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("API-Key", pushcutApiKey)
        }
        val requestBody = null

        val requestEntity = HttpEntity(requestBody, headers)

        val response = restTemplate.exchange(pushcutUrl, HttpMethod.POST, requestEntity, String::class.java)

        println("Pushcut response: ${response.statusCode}")
    }
}

