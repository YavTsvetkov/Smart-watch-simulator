package com.src.smartwatchsimulator
import android.os.Parcel
import android.os.Parcelable
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
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
import kotlinx.serialization.Serializable
import java.util.Random

@Serializable
data class SensorData(
    val distance: Double,
    val calories: Int,
    val heartRate: Int,
    val avgSpeed: Double,
    val timestamp: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(distance)
        parcel.writeInt(calories)
        parcel.writeInt(heartRate)
        parcel.writeDouble(avgSpeed)
        parcel.writeLong(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SensorData> {
        override fun createFromParcel(parcel: Parcel): SensorData {
            return SensorData(parcel)
        }

        override fun newArray(size: Int): Array<SensorData?> {
            return arrayOfNulls(size)
        }
    }
}

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
        jackson()
    }

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
    embeddedServer(Netty, port = 9669, module = Application::module).start(wait = true)
}