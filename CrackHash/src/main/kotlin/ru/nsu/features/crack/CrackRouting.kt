package ru.nsu.features.crack

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.nsu.data.Request
import ru.nsu.data.Requests
import ru.nsu.data.Status
import java.util.*

fun Application.configureCrackRouting(){
    routing {
        post("/api/hash/crack"){
            val receive = call.receive(CrackRemoteRequest::class)
            val token = UUID.randomUUID().toString()
            val client = HttpClient(CIO){
                install(ContentNegotiation) {
                    json()
                }
            }

            val response = client.post("http://worker:8008/internal/api/worker/hash/crack/task"){
                contentType(ContentType.Application.Json)
                setBody(WorkerRequest(token, 1, 1, receive.hash, receive.maxLength))
            }
            if(response.status.isSuccess()){
                Requests.requests[token] = Request(token, "none", Status.IN_PROGRESS)
                println(Requests.requests)
                call.respond(CrackRemoteResponce(token))
            } else{
                call.respond(HttpStatusCode.BadRequest, "Error starting the job of worker!")
            }
        }
    }
}
