package com.example.isodfeediosnotifier.feed.application

import com.example.isodfeediosnotifier.feed.core.port.FeedService
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service

@Service
class FeedChecker(private val feedService: FeedService) {

    @PostConstruct
    fun startChecking() {
        feedService.startChecking()

    }
}