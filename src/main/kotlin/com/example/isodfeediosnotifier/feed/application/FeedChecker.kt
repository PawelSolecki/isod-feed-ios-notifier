package com.example.isodfeediosnotifier.feed.application

import com.example.isodfeediosnotifier.feed.domain.model.FeedDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class FeedChecker(
    private val feedService: FeedService,
    private val feedNotifier: FeedNotifier
) {
    private var lastProcessedFeed: FeedDTO? = null

    @Scheduled(fixedDelayString = "\${check-interval}")
    fun checkForNewFeed() {
        println("Checking for new feed")
        val latestFeed = feedService.fetchLatestFeed()
        if (latestFeed != null && latestFeed != lastProcessedFeed) {
            lastProcessedFeed = latestFeed
            println("New feed found: $latestFeed")
            feedNotifier.sendNotification(latestFeed)
        }

    }
}