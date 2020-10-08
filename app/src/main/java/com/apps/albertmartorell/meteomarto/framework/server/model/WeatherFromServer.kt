package com.apps.albertmartorell.meteomarto.framework.server.model

data class WeatherFromServer(
    var main: String? = "",
    var description: String? = "",
    var icon: String? = ""
)
