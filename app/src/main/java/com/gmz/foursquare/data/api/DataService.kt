package com.gmz.foursquare.data.api

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object DataService {
    const val LOGIN_URL = "https://foursquare.com/"
    const val BASE_URL = "https://api.foursquare.com/v2/"
    private val TIMEOUT: Long = 15000

    fun getLoginService(): DataApi {
        return getService(LOGIN_URL)
    }

    fun getDataService(): DataApi {
        return getService(BASE_URL)
    }

    fun getService(url: String): DataApi {
        val builder = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
        val clientBuilder = getClientBuilder()
        clientBuilder?.let { builder.client(clientBuilder.build()) }
        return builder.build()
            .create(DataApi::class.java)
    }

    private fun getClientBuilder(): OkHttpClient.Builder? {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
        client.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
            .protocols(Arrays.asList(Protocol.HTTP_1_1))
            .addInterceptor(loggingInterceptor)
        return client
    }

}