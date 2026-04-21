package com.amonteiro.a04_sdv_parisa.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

//Suspend sera expliqué dans le chapitre des coroutines
suspend fun main() {
    val weathers = KtorWeatherApi.loadWeathers("Nice")

    for(weather in weathers) {
        println(weather.getResume())
    }

    KtorWeatherApi.close()
}

object KtorWeatherApi {
    private const val API_URL =
        "https://api.openweathermap.org/data/2.5/find?appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr&q="

    //Création et réglage du client
    private val client = HttpClient {
        install(Logging) {
            //(import io.ktor.client.plugins.logging.Logger)
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.INFO  // TRACE, HEADERS, BODY, etc.
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
        //engine { proxy = ProxyBuilder.http("monproxy:1234") }
    }

    suspend fun loadWeathers(cityName: String): List<WeatherEntity> {
        val response = client.get(API_URL + cityName)
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }

        val weatherResponseDTO : WeatherResponseDTO = response.body()

        return weatherResponseDTO.list
    }

    //Ferme le Client mais celui ci ne sera plus utilisable. Uniquement pour le main
    fun close() = client.close()

}

//DATA CLASS

//Possible qu'il y ait besoin de cette annotation en fonction du compilateur
@Serializable //KotlinX impose cette annotation
data class WeatherResponseDTO(val list: List<WeatherEntity>)

@Serializable //KotlinX impose cette annotation
data class WeatherEntity(
    val id: Long,
    val name: String,
    val main: TempEntity,
    val wind: WindEntity,
    val weather: List<DescriptionEntity>
) {
    fun getResume() = """
        Il fait ${main.temp}° à $name (id=$id) avec un vent de ${wind.speed} m/s
        -Description : ${weather.firstOrNull()?.description ?: "-"}
        -Icône : ${weather.firstOrNull()?.icon ?: "-"}
    """.trimIndent()
}

@Serializable
data class DescriptionEntity(val description: String, val icon: String)

@Serializable
data class WindEntity(val speed: Double)

@Serializable //KotlinX impose cette annotation
data class TempEntity(val temp: Double)