package com.apps.albertmartorell.meteomarto.framework.server.model

import com.google.gson.annotations.SerializedName

data class WindFromServer(
    val speed: Float? = 0.toFloat(),
    @SerializedName("deg")
    val degrees: Float? = 0.toFloat()
)
