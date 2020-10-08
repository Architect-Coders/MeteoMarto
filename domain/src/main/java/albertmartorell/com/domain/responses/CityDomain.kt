package albertmartorell.com.domain.responses

import albertmartorell.com.domain.cityweather.*
import com.google.gson.annotations.SerializedName

data class CityDomain(
    @SerializedName("coord") var coordinates: Coordinates?,
    var weather: List<Weather>?,
    var main: Main?,
    var visibility: Long? = 0,
    var wind: Wind?,
    var clouds: Clouds?,
    var sys: Sys?,
    var name: String? = ""
)
