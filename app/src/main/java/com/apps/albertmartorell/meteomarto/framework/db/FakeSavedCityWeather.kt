package com.apps.albertmartorell.meteomarto.framework.db

import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.domain.cityforecast.ForecastDomain
import albertmartorell.com.domain.responses.CityDomain
import kotlinx.coroutines.flow.Flow

/**
 * It implements one dependency offered by the data layer
 *
 * This could be implemented by Room, but to make the example easier I will use fake implementations.
 */
class FakeSavedCityWeather : WeatherRepository.WeatherDeviceSource {

//    override suspend fun getCityWeatherByName(name: String): City {
//
//        lateinit var city: City
////        city.name = name
////        city.visibility = 75
////
////        lateinit var clouds: Clouds
////        clouds.coverage = 100F
////        city.clouds = clouds
////
////        lateinit var coordinates: Coordinates
////        coordinates.latitude = 45F
////        coordinates.longitude = 2F
////        city.coordinates = coordinates
////
////        lateinit var sys: Sys
////        sys.country = "Catalunya"
////        sys.message = 5F
////        sys.type = 8
////        sys.sunrise = 51
////        sys.sunset = 6
////        city.sys = sys
////
////        lateinit var weather: Weather
////        weather.description = "Neu generalitzada. Precipitacions fortes"
////        weather.icon = "Neu"
////        weather.main = "Invasió siberiana de primera categoria"
////        val weatherList = mutableListOf<Weather>()
////        weatherList.add(weather)
////        city.weather = weatherList
////
////        lateinit var main: Main
////        main.humidity = 80F
////        main.pressure = 990F
////        main.temperature = -6F
////        main.temperatureMax = -5F
////        main.temperatureMin - 11F
////        city.main = main
//
//        return city
//
//    }

    override suspend fun isEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveCityWeather(cityWeather: CityDomain) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override suspend fun getCity(): Flow<CityDomain> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAllCities() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAllForecast() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveForecastCity(forecastDomain: List<ForecastDomain>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getForecastCity(): Flow<List<ForecastDomain>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
