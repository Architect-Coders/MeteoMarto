package albertmartorell.com.data.repositories

import albertmartorell.com.domain.cityforecast.ForecastDomain
import albertmartorell.com.domain.responses.CityDomain
import kotlinx.coroutines.flow.Flow

// We use the Repository pattern, which its main purpose is to abstract the concrete implementation of data access. To achieve this, I will add one interface and one class for each model
// Using the repository pattern is a good example of the Dependency Inversion Principle because an more abstract layer (data) does not depend on a more specific layer (framework),
// so the repository is an abstraction that do not depend on details

// The repository will user a couple of sources: one has access to the current weather (server) and the other to the saved weather (device). Both sources are interfaces which allow us the dependency inversion.
// The data layer does not know (and it does not need it) which is the implementation of these interfaces. The current and saved weathers must be manage by the specific frameworks (retrofit and room in this scope)
// Now the repository can user both sources without need its implementation
class WeatherRepository(
    private val serverSource: WeatherServerSource,
    private val deviceSource: WeatherDeviceSource
) {

    suspend fun saveCityWeather(city: CityDomain) {

        deviceSource.saveCityWeather(city)

    }

    suspend fun getCityWeatherFromDatabase(): Flow<CityDomain> {

        return deviceSource.getCity()

    }

    suspend fun requestWeatherByCoordinates(latitude: Float?, longitude: Float?): CityDomain {

        return serverSource.getWeatherByCoordinates(latitude, longitude)

    }

    suspend fun deleteAllCities() {

        deviceSource.deleteAllCities()

    }

    suspend fun requestCityForecastByCoordinates(
        latitude: Float?,
        longitude: Float?
    ): List<ForecastDomain> =
        serverSource.requestCityForecastByCoordinates(latitude, longitude)

    suspend fun deleteAllForecast() {

        deviceSource.deleteAllForecast()

    }

    suspend fun saveForecastCity(forecastDomain: List<ForecastDomain>) {

        deviceSource.saveForecastCity(forecastDomain)

    }

    suspend fun getForecastCityFromDatabase(): Flow<List<ForecastDomain>> {

        return deviceSource.getForecastCity()

    }

    /**
     * The interface that the framework layer must implement
     *
     */
    interface WeatherServerSource {

        suspend fun getWeatherByCoordinates(
            latitude: Float?,
            longitude: Float?
        ): CityDomain

        suspend fun requestCityForecastByCoordinates(
            latitude: Float?,
            longitude: Float?
        ): List<ForecastDomain>

    }

    interface WeatherDeviceSource {

        suspend fun isEmpty(): Boolean
        suspend fun saveCityWeather(cityWeather: CityDomain)
        suspend fun getCity(): Flow<CityDomain>
        suspend fun deleteAllCities()
        suspend fun deleteAllForecast()
        suspend fun saveForecastCity(forecastDomain: List<ForecastDomain>)
        suspend fun getForecastCity(): Flow<List<ForecastDomain>>
    }

}
