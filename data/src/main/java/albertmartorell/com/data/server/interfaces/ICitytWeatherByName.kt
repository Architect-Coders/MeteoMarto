package albertmartorell.com.data.server.interfaces

import albertmartorell.com.domain.responses.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ICitytWeatherByName {

    // Preguntar a Antonio Leiva si aquí la funció hauria de ser suspend
    // In this case suspend indicates that the function can suspend the execution of a coroutine.
    // It identifies the type of the request with the notation, and then the parameters of the request as arguments of the function.
    // The return value wraps the response in a Call object with the type of the expected result.
    @GET("data/2.5/weather?")
    suspend fun getWeather(@Query("q") cityName: String): Call<WeatherResponse>

}
