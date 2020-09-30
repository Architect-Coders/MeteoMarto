package com.apps.albertmartorell

import albertmartorell.com.data.repositories.PermissionChecker
import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.data.sources.LocationDataSource
import albertmartorell.com.domain.cityforecast.ForecastDomain
import albertmartorell.com.domain.cityweather.Coordinates
import albertmartorell.com.domain.responses.City
import com.apps.albertmartorell.meteomarto.testshared.mockDomainCity
import com.apps.albertmartorell.meteomarto.testshared.mockFlowCity
import com.apps.albertmartorell.meteomarto.testshared.mockForecastDomain
import com.apps.albertmartorell.meteomarto.ui.dataModule
import com.apps.albertmartorell.meteomarto.ui.useCasesModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {

    startKoin {
        modules(listOf(mockedAppModule, dataModule, useCasesModule) + modules)
    }

}

private val mockedAppModule = module {

    factory<WeatherRepository.WeatherServerSource> { FakeRemoteDataSource() }
    factory<WeatherRepository.WeatherDeviceSource> { FakeLocalDataSource() }
    factory<LocationDataSource> { FakeLocationDataSource() }
    factory<PermissionChecker> { FakePermissionChecker() }
    // to test the Coroutines and keep in the same thread they are executed
    single { Dispatchers.Unconfined }

}

val fakeCoordinates = Coordinates(41.54329F, 2.10942F)

val defaultFakeCityWeather =
    mockDomainCity(fakeCoordinates, "FakeCityName")

val defaultFakeCityForecast = listOf(mockForecastDomain())

class FakeLocalDataSource : WeatherRepository.WeatherDeviceSource {

    private lateinit var cityWeather: City
    private var cityForecast = emptyList<ForecastDomain>()


    override suspend fun isEmpty() = false

    override suspend fun saveCityWeather(cityWeather: City) {

        this.cityWeather = cityWeather

    }

    override suspend fun getCity() = mockFlowCity(cityWeather.coordinates!!, cityWeather.name!!)

    override suspend fun deleteAllCities() {}

    override suspend fun deleteAllForecast() {

        cityForecast = emptyList()

    }

    override suspend fun saveForecastCity(forecastDomain: List<ForecastDomain>) {

        cityForecast = forecastDomain

    }

    override suspend fun getForecastCity() = flowOf(cityForecast)

}

class FakeRemoteDataSource : WeatherRepository.WeatherServerSource {

    override suspend fun getWeatherByCoordinates(latitude: Float?, longitude: Float?) =
        defaultFakeCityWeather

    override suspend fun requestCityForecastByCoordinates(latitude: Float?, longitude: Float?) =
        defaultFakeCityForecast

}

class FakePermissionChecker : PermissionChecker {

    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}

class FakeLocationDataSource : LocationDataSource {

    override suspend fun findLastRegion() = fakeCoordinates

}
