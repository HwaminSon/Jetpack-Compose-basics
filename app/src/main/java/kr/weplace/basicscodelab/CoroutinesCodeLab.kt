package kr.weplace.basicscodelab

import kotlin.system.*
import kotlinx.coroutines.*

fun main() {
    val time = measureTimeMillis {
        runBlocking {
            println("Weather forecast")
            println(getWeatherReport())
            println("Have a good day!")
        }
    }
    println("Execution time: ${time / 1000.0} seconds")
}
suspend fun getForecast(): String {
    println("getForecast start")
    delay(1000)
    println("getForecast end")
    return "Sunny"
}

suspend fun getTemperature(): String {
    println("getTemperature start")
    delay(5000)
    throw AssertionError("Temperature is invalid")
    println("getTemperature end")
    return "30\u00b0C"
}

suspend fun getWeatherReport() = coroutineScope {
    val forecast = async { getForecast() }
    val temperature = async {
        try {
            getTemperature()
        } catch (e: AssertionError) {
            println("Caught exception $e")
            "{ No temperature found }"
        }
    }

    delay(200)

    temperature.cancel()

    "날씨는 ${forecast.await()}"
}
