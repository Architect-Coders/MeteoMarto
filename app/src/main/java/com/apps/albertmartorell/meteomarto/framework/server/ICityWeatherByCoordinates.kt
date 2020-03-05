package albertmartorell.com.data.server.interfaces

import albertmartorell.com.domain.responses.City
import albertmartorell.com.domain.responses.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ICityWeatherByCoordinates {

    // In this case suspend indicates that the function can suspend the execution of a coroutine.
    // It identifies the type of the request with the notation, and then the parameters of the request as arguments of the function.
    // The return value wraps the response in a Call object with the type of the expected result.
    @GET("data/2.5/weather?&APPID=5e11044c499339f5ede8d58d85d134d3")
    suspend fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String): City

    @GET("data/2.5/forecast?&APPID=5e11044c499339f5ede8d58d85d134d3")
    suspend fun getForecast(@Query("lat") lat: String, @Query("lon") lon: String): ForecastResponse

    // Example: //http://openweathermap.org/img/wn/11d@2x.png
    @GET("/img/wn/")
    suspend fun getWeatherIcon(@Url url: String): String

}
