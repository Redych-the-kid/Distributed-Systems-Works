package ru.nsu.data

import java.util.concurrent.ConcurrentHashMap

object Requests {
    val requests = ConcurrentHashMap<String, Request>()
}

data class Request(
    val requestId : String,
    var value : String,
    var status : Status
)

enum class Status {
    IN_PROGRESS, READY, ERROR
}