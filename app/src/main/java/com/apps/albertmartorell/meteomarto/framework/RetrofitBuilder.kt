package com.apps.albertmartorell.meteomarto.framework

import albertmartorell.com.data.server.interfaces.ICityWeatherByCoordinates
import albertmartorell.com.data.server.interfaces.ICitytWeatherByName
import com.apps.albertmartorell.meteomarto.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit builder, which is a singleton object
 */
object RetrofitBuilder {

    val baseUrl = "http://api.openweathermap.org/"
    private val safeHttpClient = OkHttpClient().newBuilder()

        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })

        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()


    private val retrofit: Retrofit by lazy {

        Retrofit.Builder()
            .client(safeHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val cityWeatherByName by lazy { retrofit.create(ICitytWeatherByName::class.java) }
    val cityWeatherByCoordinates by lazy { retrofit.create(ICityWeatherByCoordinates::class.java) }

}