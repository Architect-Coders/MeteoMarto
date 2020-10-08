package albertmartorell.com.usecases

import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.domain.responses.CityDomain

/**
 * Request city weather by its coordinates from a server service
 */
class RequestWeatherByCoordinates(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(_latitude: Float?, _longitude: Float?): CityDomain {

        return weatherRepository.requestWeatherByCoordinates(_latitude, _longitude)

    }

}
