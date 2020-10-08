package com.apps.albertmartorell.meteomarto.framework.db

import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.domain.cityforecast.ForecastDomain
import albertmartorell.com.domain.responses.CityDomain
import android.content.Context
import com.apps.albertmartorell.meteomarto.framework.convertForecastDomainToEntity
import com.apps.albertmartorell.meteomarto.framework.convertToDomain
import com.apps.albertmartorell.meteomarto.framework.convertToResponse
import com.apps.albertmartorell.meteomarto.framework.saveCityAsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * It implements one dependency offered by the data layer, in this case the WeatherDeviceSource
 *
 */
class ImpWeatherDeviceSource(context: Context) : WeatherRepository.WeatherDeviceSource {

    // Use database to get an instance of WeatherDao and store it in local field
    private val dao = MeteoMartoDatabase.getInstance(context).weatherDao()

    override suspend fun isEmpty(): Boolean {

        return dao.getAll().count() == 0

    }

    override suspend fun saveCityWeather(cityWeather: CityDomain) {

        dao.insertWeatherCity(cityWeather.saveCityAsEntity())

    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    override suspend fun getCity(): Flow<CityDomain> {

        return dao.getCity().map { it.convertToResponse() }.distinctUntilChanged()

    }

    override suspend fun deleteAllCities() {

        return dao.deleteAllCities()

    }

    override suspend fun deleteAllForecast() {

        return dao.deleteAllForecast()

    }

    override suspend fun saveForecastCity(forecastDomain: List<ForecastDomain>) {

        dao.insertForecastCity(convertForecastDomainToEntity(forecastDomain))

    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    override suspend fun getForecastCity(): Flow<List<ForecastDomain>> {

        return dao.getForecastCity().map { it.convertToDomain() }.distinctUntilChanged()

    }

}
