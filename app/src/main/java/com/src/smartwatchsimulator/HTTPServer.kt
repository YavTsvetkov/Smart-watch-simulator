package com.src.smartwatchsimulator

//import io.ktor.features.ContentNegotiation
//import io.ktor.features.StatusPages
//import io.ktor.html.respondHtml
//import io.ktor.http.HttpStatusCode
//import io.ktor.http.content.resources
//import io.ktor.http.content.static
//import io.ktor.jackson.jackson
//import io.ktor.request.receive
//import io.ktor.routing.*
//import io.ktor.server.engine.embeddedServer
//import io.ktor.server.netty.Netty
//import io.ktor.util.pipeline.PipelineContext
//import kotlinx.
//import kotlinx.html.h1
//import kotlinx.html.p

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.p
import java.util.Random

data class SensorData(
    val distance: Double,
    val calories: Int,
    val heartRate: Int,
    val avgSpeed: Double,
    val timestamp: Long
)

fun generateRandomSensorData(): SensorData {
    val random = Random()
    return SensorData(
        distance = random.nextDouble() * 100,
        calories = random.nextInt(500),
        heartRate = random.nextInt(150),
        avgSpeed = random.nextDouble() * 20,
        timestamp = System.currentTimeMillis()
    )
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

//    install(StatusPages) {
//        exception<Throwable> { cause ->
//            call.respond(HttpStatusCode.InternalServerError, "Internal Server Error: ${cause.localizedMessage}")
//        }
//    }

    routing {
        route("/api") {
            get("/sensorData") {
                val data = generateRandomSensorData()
                call.respond(data)
            }

            post("/receiveSensorData") {
                val receivedData = call.receive<SensorData>()
                // Process the received data as needed
                call.respond(HttpStatusCode.OK)
            }
        }

        static("/static") {
            resources("static")
        }
    }

    routing {
        get("/") {
            call.respondHtml {
                body {
                    h1 {
                        +"Android HTTP Server"
                    }
                    p {
                        +"Visit /api/sensorData to get random sensor data."
                    }
                }
            }
        }
    }
}

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

class HTTPServer {

}