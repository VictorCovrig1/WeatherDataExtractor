package org.example.api

import com.google.gson.Gson
import org.example.DAYS_NUMBER
import org.example.KEY
import org.example.models.ErrorResponse
import org.example.models.ForecastResponse
import org.example.objects.RetrofitProvider
import retrofit2.Response

// this function will call get forecast api
suspend fun getForecast(city: String, forecastDate: String, hour: Int) : ForecastResponse? {
    var response : Response<ForecastResponse>? = null

    try {
        // will call getForecast defined in IApiService interface
        response = RetrofitProvider.apiService.getForecast(city,
            DAYS_NUMBER, hour, forecastDate, KEY)

        // in case of success, return the requested object
        if(response.isSuccessful)
            return response.body()
        else
        {
            // in case of error code other than 200, print http code, inner error code and message
            val errorBodyString = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBodyString, ErrorResponse::class.java).error
            println("HTTP Code: ${response.code()} -> Inner Error Code: " +
                "${errorResponse.code} -> Message: ${errorResponse.message}")
            return null
        }
    }
    catch (ex: Exception) {
        // in case of error code another than 200 it will throw an exception and print the error code
        println(ex.message)
        return null
    }
}

