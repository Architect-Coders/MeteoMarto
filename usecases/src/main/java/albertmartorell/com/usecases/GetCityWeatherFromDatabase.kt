package albertmartorell.com.usecases

import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.domain.responses.CityDomain
import kotlinx.coroutines.flow.Flow

class GetCityWeatherFromDatabase(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(): Flow<CityDomain> {

        return weatherRepository.getCityWeatherFromDatabase()

    }

}
