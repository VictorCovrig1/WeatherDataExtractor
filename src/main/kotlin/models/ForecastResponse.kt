package org.example.models

import com.google.gson.annotations.SerializedName

// define response object, extracting only needed information from response
data class ForecastResponse(@SerializedName("forecast") val forecastData : ForecastData,
                            @SerializedName("location") val location : Location)

data class Location(@SerializedName("name") val name: String)

data class ForecastData(@SerializedName("forecastday") val forecastDayData : List<ForecastDay>)

data class ForecastDay(@SerializedName("day") val day: DayData,
                   @SerializedName("hour") val hour: List<HourData>)

data class DayData(@SerializedName("maxtemp_c") val maxTemperature : Double,
               @SerializedName("mintemp_c") val minTemperature: Double)

data class HourData(@SerializedName("humidity") val humidityPercentage : Int,
                @SerializedName("wind_kph") val windSpeed: Double,
                @SerializedName("wind_dir") val windDirection: String)