package albertmartorell.com.usecases

import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.domain.responses.CityDomain

class SaveCityWeather(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(city: CityDomain) {

        weatherRepository.saveCityWeather(city)

    }

}
