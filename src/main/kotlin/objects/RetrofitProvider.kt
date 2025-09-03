package org.example.objects

import okhttp3.OkHttpClient
import org.example.BASE_URL
import org.example.api.IApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    // define httpClient, that will be used in retrofit apiService object
    val httpClient by lazy {
        OkHttpClient.Builder().build()
    }

    // define apiService object. This object will be used for api calls (defined in IApiService interface)
    val apiService: IApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IApiService::class.java)
    }
}