package albertmartorell.com.domain

import com.google.gson.annotations.SerializedName

data class Coordinates(@SerializedName("lon") var longitude: Float? = 0.toFloat(), @SerializedName("lat") var latitude: Float? = 0.toFloat())