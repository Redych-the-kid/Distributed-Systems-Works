package ru.nsu

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.nsu.features.taskgetter.configureTaskRouting
import ru.nsu.plugins.*

fun main() {
    embeddedServer(Netty, port = 8008, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureTaskRouting()
}
