package com.example.isodfeediosnotifier.feed.application

import com.example.isodfeediosnotifier.feed.domain.model.FeedDTO
import com.example.isodfeediosnotifier.feed.infrastructure.FeedNotifierClient
import org.springframework.stereotype.Component

@Component
class FeedNotifier(
    private val feedNotifierClient: FeedNotifierClient
) {
    fun sendNotification(latestFeed: FeedDTO) {
        val requestBody = mapOf(
            "title" to latestFeed.subject,
            "text" to latestFeed.content,
            "image" to "https://isod.ee.pw.edu.pl/isod-stud/img/logo_small_ee.png",

        )
        feedNotifierClient.sendNotification(requestBody)
    }
}