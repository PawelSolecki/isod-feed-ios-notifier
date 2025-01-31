package com.example.isodfeediosnotifier.feed.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeedDTO(
    val hash: String,
    val subject: String,
    val content: String,
    val modifiedDate: String,
    val modifiedBy: String?
)