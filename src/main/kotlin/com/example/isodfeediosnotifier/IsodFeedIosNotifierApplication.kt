package com.example.isodfeediosnotifier

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class IsodFeedIosNotifierApplication

fun main(args: Array<String>) {
    runApplication<IsodFeedIosNotifierApplication>(*args)
}
