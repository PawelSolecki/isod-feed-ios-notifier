package com.example.isodfeediosnotifier.feed.core.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeedResponseDTO(
    val items: List<FeedDTO>
)
