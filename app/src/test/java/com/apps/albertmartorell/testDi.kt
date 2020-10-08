package com.apps.albertmartorell

import albertmartorell.com.data.repositories.PermissionChecker
import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.data.sources.LocationDataSource
import albertmartorell.com.domain.cityforecast.ForecastDomain
import albertmartorell.com.domain.cityweather.Coordinates
import albertmartorell.com.domain.responses.CityDomain
import com.apps.albertmartorell.meteomarto.testshared.mockDomainCity
import com.apps.albertmartorell.meteomarto.testshared.mockFlowCity
import com.apps.albertmartorell.meteomarto.testshared.mockForecastDomain
import com.apps.albertmartorell.meteomarto.ui.dataModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {

    // rep un llistat de mòduls que els afegeix a uns que ja tenim per defecte: mockedAppModule i dataModule.
    // el mòdul dataModule no el modifiquem ja que pertany al nostre domini i no fa falta que el modifiquem, en canvi appModule ha estat substituit per mockedAppModule, ja que appMOdule és el que es connecta a lllibres de tercers,
    // d'Android i per tant li hem d'aplicar les característiques que nosaltres necessitem per fer els tests.
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }

}

// llista d'elements inventats (fakes)
private val mockedAppModule = module {

    factory<WeatherRepository.WeatherServerSource> { FakeRemoteDataSource() }
    factory<WeatherRepository.WeatherDeviceSource> { FakeLocalDataSource() }
    factory<LocationDataSource> { FakeLocationDataSource() }
    factory<PermissionChecker> { FakePermissionChecker() }
    // to test the Coroutines and keep in the same thread they are executed
    single { Dispatchers.Unconfined }

}

val defaultFakeCoordinates = Coordinates(41.54329F, 2.10942F)

val defaultFakeCityWeather =
    mockDomainCity(defaultFakeCoordinates, "FakeCityName")

val defaultFakeCityForecast = listOf(mockForecastDomain())

// It simulates the Room library. Here it is like a cache memory
class FakeLocalDataSource : WeatherRepository.WeatherDeviceSource {

    private lateinit var cityWeather: CityDomain
    private var cityForecast = emptyList<ForecastDomain>()


    override suspend fun isEmpty() = false

    override suspend fun saveCityWeather(cityWeather: CityDomain) {

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

// It simulates the Retrofit library
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

    override suspend fun findLastRegion() = defaultFakeCoordinates

}
