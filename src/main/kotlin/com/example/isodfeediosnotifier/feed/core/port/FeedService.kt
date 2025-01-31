package com.example.isodfeediosnotifier.feed.core.port

interface FeedService {
    fun startChecking()
    fun sendNotification()
}