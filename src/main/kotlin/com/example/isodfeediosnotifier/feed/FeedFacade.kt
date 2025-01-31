package com.example.isodfeediosnotifier.feed

import com.example.isodfeediosnotifier.feed.core.model.FeedDTO
import com.example.isodfeediosnotifier.feed.core.port.FeedService
import org.springframework.web.client.RestTemplate
import java.util.concurrent.TimeUnit

class FeedFacade(private val restTemplate: RestTemplate) : FeedService {
    override fun startChecking() {
        Thread {
            while (true) {
                try {
                    val feed = checkForNewFeed()

                    if (feed != null) {
                        println("[!] New announcement: ${feed.title}")
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
            val response = restTemplate.getForObject("http://localhost:8000/api/feed", Array<FeedDTO>::class.java)

            val latestFeed = response?.lastOrNull()

            if (latestFeed != null && latestFeed != lastProcessedFeed) {
                this.lastProcessedFeed = latestFeed  // Aktualizujemy ostatnie ogłoszenie
                latestFeed
            } else {
                null
            }
        } catch (e: Exception) {
            println("Error while fetching announcement: ${e.message}")
            null
        }
    }
}
