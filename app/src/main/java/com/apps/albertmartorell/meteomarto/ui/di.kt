package com.apps.albertmartorell.meteomarto.ui

import albertmartorell.com.data.repositories.PermissionChecker
import albertmartorell.com.data.repositories.RegionRepository
import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.data.sources.LocationDataSource
import albertmartorell.com.usecases.*
import android.app.Application
import com.apps.albertmartorell.meteomarto.framework.Interactors
import com.apps.albertmartorell.meteomarto.framework.db.ImpWeatherDeviceSource
import com.apps.albertmartorell.meteomarto.framework.server.ImpWeatherServerSource
import com.apps.albertmartorell.meteomarto.ui.city.CityViewModel
import com.apps.albertmartorell.meteomarto.ui.common.AndroidPermissionChecker
import com.apps.albertmartorell.meteomarto.ui.common.PlayServicesLocationDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun Application.initDI() {

    // to create the dependencies graph
    startKoin {

        // log
        androidLogger()
        // the android context to used in these modules. It is an extra dependency
        androidContext(this@initDI)
        // the modules to load
        modules(listOf(appModule, dataModule, androidModule))

    }

}

// there are either third party and Android libraries
private val appModule = module {

    factory<WeatherRepository.WeatherServerSource> { ImpWeatherServerSource() }
    factory<WeatherRepository.WeatherDeviceSource> { ImpWeatherDeviceSource(androidApplication()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(androidApplication()) }
    factory<PermissionChecker> { AndroidPermissionChecker(androidApplication()) }
    single<CoroutineDispatcher> { Dispatchers.Main }

}

// it belongs to own domain scope
val dataModule = module {

    // get is LocationDataSource and PermissionChecker, which previously were built in the appModule
    factory { RegionRepository(get(), get()) }
    factory { WeatherRepository(get(), get()) }
    single { FindCurrentRegion(get()) }
    single { SaveCityWeather(get()) }
    single { RequestWeatherByCoordinates(get()) }
    single { DeleteAllCities(get()) }
    single { RequestCityForecastByCoordinates(get()) }
    single { DeleteAllForecast(get()) }
    single { SaveForecastCity(get()) }
    single { GetForecastCityFromDatabase(get()) }
    single { GetCityWeatherFromDatabase(get()) }
    single { Interactors(get(), get(), get(), get(), get(), get(), get(), get(), get()) }

}

// here we have the specific Android artifacts, for example activities, fragments
private val androidModule = module {

    viewModel {

        CityViewModel(get(), get())

    }

}
