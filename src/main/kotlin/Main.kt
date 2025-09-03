package org.example

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.example.api.getForecast
import org.example.helpers.buildTable
import org.example.models.ForecastResponse
import org.example.objects.RetrofitProvider
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    try {
        val cities = CITIES_LIST.split(",")

        val forecastDate = LocalDate.now().plus(1, ChronoUnit.DAYS).toString()
        val hour = LocalTime.now().hour

        val deferreds : MutableList<Deferred<ForecastResponse?>> = mutableListOf()
        var results : List<ForecastResponse?>

        // not using bulk call, because it is not available for free plan (only Pro+ and above)
        runBlocking {
            // Dispatchers.IO is used for optimizing blocking I/O operations (in this case network call)
            withContext(Dispatchers.IO) {
                // will run the api calls in parallel, for performance
                for(city in cities) {
                    deferreds.add(async {
                        getForecast(city, forecastDate, hour)
                    })
                }
                // getting results from deffereds (promises)
                results = deferreds.awaitAll()
            }
        }
        val nonNullResults = results.filterNotNull()

        if(nonNullResults.isNotEmpty())
            // in case of at least one result for one city, build the table
            println(buildTable(nonNullResults).toString())
        else
            // in case of not getting any results print the message
            println("Could not get any results.")
    }
    catch(ex: Exception) {
        println(ex.message)
    }
    finally {
        closeHttpClient()
    }
}

fun closeHttpClient() {
    // closing the httpclient resources to avoid idle connections
    RetrofitProvider.httpClient.dispatcher.executorService.shutdown()
    RetrofitProvider.httpClient.connectionPool.evictAll()
    RetrofitProvider.httpClient.cache?.close()
}