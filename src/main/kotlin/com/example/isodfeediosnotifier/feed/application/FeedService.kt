package com.example.isodfeediosnotifier.feed.application

import com.example.isodfeediosnotifier.feed.domain.model.FeedDTO
import com.example.isodfeediosnotifier.feed.domain.model.FeedResponseDTO
import com.example.isodfeediosnotifier.feed.infrastructure.FeedHttpClient
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
class FeedService(
    private val feedHttpClient: FeedHttpClient,
    private val objectMapper: ObjectMapper
) {

    fun fetchLatestFeed(): FeedDTO? {
        val stringResponse = feedHttpClient.getFeedList()
        val feedList = objectMapper.readValue(stringResponse, FeedResponseDTO::class.java)

        return feedList.items.firstOrNull()
    }
}