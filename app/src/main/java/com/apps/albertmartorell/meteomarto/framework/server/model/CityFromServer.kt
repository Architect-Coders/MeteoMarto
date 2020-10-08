package com.apps.albertmartorell.meteomarto.framework.server.model

import com.google.gson.annotations.SerializedName

data class CityFromServer(
    @SerializedName("coord") var coordinates: CoordinatesFromServer?,
    var weather: List<WeatherFromServer>?,
    var main: MainFromServer?,
    var visibility: Long? = 0,
    var wind: WindFromServer?,
    var clouds: CloudsFromServer?,
    var sys: SysFromServer?,
    var name: String? = ""
)
