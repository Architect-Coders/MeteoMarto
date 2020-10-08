package com.apps.albertmartorell.meteomarto.framework.server.model

import com.google.gson.annotations.SerializedName

data class CoordinatesFromServer(@SerializedName("lat") var latitude: Float?, @SerializedName("lon") var longitude: Float?)
