package org.example.api

import org.example.models.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {
    // the call forecast.json is defined, with all parameters needed
    @GET("forecast.json")
    suspend fun getForecast(@Query("q") cities : String,
                            @Query("days") daysNumber : Int,
                            @Query("hour") hour : Int,
                            @Query("dt") date : String,
                            @Query("key") apiKey : String) : Response<ForecastResponse>
}