package com.apps.albertmartorell.meteomarto.framework.server.model

import com.google.gson.annotations.SerializedName

data class MainFromServer(
    @SerializedName("temp") var temperature: Float? = 0.toFloat(),
    var humidity: Float? = 0.toFloat(),
    var pressure: Float? = 0.toFloat(),
    @SerializedName("temp_min") var temperatureMin: Float? = 0.toFloat(),
    @SerializedName("temp_max") var temperatureMax: Float? = 0.toFloat(),
    @SerializedName("feels_like") var temperatureFeelsLike: Float? = 0.toFloat()
)
