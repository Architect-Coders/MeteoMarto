package com.apps.albertmartorell.meteomarto.framework.db.daos

import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.domain.responses.WeatherResponse

/**
 * It implements one dependency offered by the data layer, in this case the WeatherDeviceSource
 *
 */
class RoomCityWeather : WeatherRepository.WeatherDeviceSource {

    override suspend fun getCityWeatherByName(name: String): WeatherResponse {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override suspend fun getCityWeatherByCoordinates(
        latitude: Float,
        longitude: Float

    ): WeatherResponse {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

}