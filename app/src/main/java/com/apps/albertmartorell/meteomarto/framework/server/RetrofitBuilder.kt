package com.apps.albertmartorell.meteomarto.framework.server

import albertmartorell.com.data.server.interfaces.ICityWeatherByCoordinates
import albertmartorell.com.data.server.interfaces.ICityWeatherByName
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

    const val iconsPrefixWeatherUrl ="https://openweathermap.org/img/wn/"
    const val iconsSuffixWeatherUrl="@2x.png"
    private const val baseUrl = "https://api.openweathermap.org/"
    private val safeHttpClient = OkHttpClient().newBuilder()

        .connectTimeout(60, TimeUnit.SECONDS)
        //We recommend to add logging as the last interceptor, because this will also log the information
        // which you added with previous interceptors to your request.
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })

        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    // Using "lazy" delegate, which grants that the instance will not be initialized unless it is used
    private val retrofit: Retrofit by lazy {

        Retrofit.Builder()
            .client(safeHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    // Global properties: they are a singleton dependency in a lazy fashion
    val cityWeatherByName by lazy { retrofit.create(ICityWeatherByName::class.java) }
    val cityWeatherByCoordinates by lazy { retrofit.create(ICityWeatherByCoordinates::class.java) }

}