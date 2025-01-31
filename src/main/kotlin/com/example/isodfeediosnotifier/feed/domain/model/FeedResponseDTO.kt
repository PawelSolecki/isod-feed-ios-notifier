package com.example.isodfeediosnotifier.feed.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeedResponseDTO(
    val items: List<FeedDTO>
)
