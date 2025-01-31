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
    @Value("\${isod-api-key}")
    lateinit var isodApiKey: String

    @Value("\${isod-username}")
    lateinit var isodUsername: String

    @Value("\${pushcat-api-key}")
    lateinit var pushcutApiKey: String

    @Value("\${pushcat-notification-name}")
    lateinit var pushcutNotificationName: String

    private val isodUrl: String
        get() = "http://isod.ee.pw.edu.pl/isod-portal/wapi?q=mynewsfull&username=${isodUsername}&apikey=${isodApiKey}"

    private val pushcutUrl: String
        get() = "https://api.pushcut.io/v1/notifications/${pushcutNotificationName}"

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

//        val requestBody = mapOf(
//            "title" to lastProcessedFeed?.subject,
//            "text" to lastProcessedFeed?.content,
//            "image" to "https://isod.ee.pw.edu.pl/isod-stud/img/logo_small_ee.png",
//
//        )
        val requestBody = null

        val requestEntity = HttpEntity(requestBody, headers)

        val response = restTemplate.exchange(pushcutUrl, HttpMethod.POST, requestEntity, String::class.java)

        println("Pushcut response: ${response.statusCode}")
    }
}

