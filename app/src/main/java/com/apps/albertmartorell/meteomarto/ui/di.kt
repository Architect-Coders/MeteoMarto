package com.apps.albertmartorell.meteomarto.ui

import albertmartorell.com.data.repositories.PermissionChecker
import albertmartorell.com.data.repositories.RegionRepository
import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.data.sources.LocationDataSource
import albertmartorell.com.usecases.*
import android.app.Application
import com.apps.albertmartorell.meteomarto.framework.db.ImpWeatherDeviceSource
import com.apps.albertmartorell.meteomarto.framework.server.ImpWeatherServerSource
import com.apps.albertmartorell.meteomarto.ui.city.CityViewModel
import com.apps.albertmartorell.meteomarto.ui.common.AndroidPermissionChecker
import com.apps.albertmartorell.meteomarto.ui.common.PlayServicesLocationDataSource
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

// the name of the module belongs to the application module: app, use cases, data, domain....
// the dependencies that it has, are the classes that we initialize in this module
private val appModule = module {

    factory<WeatherRepository.WeatherServerSource> { ImpWeatherServerSource() }
    factory<WeatherRepository.WeatherDeviceSource> { ImpWeatherDeviceSource(androidApplication()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(androidApplication()) }
    factory<PermissionChecker> { AndroidPermissionChecker(androidApplication()) }

}

val dataModule = module {

    // get is LocationDataSource and PermissionChecker, which previously were built in the appModule
    factory { RegionRepository(get(), get()) }
    factory { WeatherRepository(get(), get()) }

}

val useCasesModule = module {

    // get RegionRepository, which previously was built in the dataModule
    factory<FindCurrentRegion> { get() }
    // get is WeatherRepository, which previously was built in the appModule
    factory<SaveCityWeather> { get() }
    factory<RequestWeatherByCoordinates> { get() }
    factory<DeleteAllCities> { get() }
    factory<RequestCityForecastByCoordinates> { get() }
    factory<DeleteAllForecast> { get() }
    factory<SaveForecastCity> { get() }
    factory<GetForecastCityFromDatabase> { get() }
    factory<GetCityWeatherFromDatabase> { get() }
    single { Dispatchers.Unconfined }

}

private val androidModule = module {

    // these dependencies can only be used in the Landing activity
    //scope(named<Landing>()) {


    viewModel {

        CityViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get())

    }

    // get RegionRepository, which previously was built in the dataModule
    single { FindCurrentRegion(get()) }
    single { SaveCityWeather(get()) }
    single { RequestWeatherByCoordinates(get()) }
    single { DeleteAllCities(get()) }
    single { RequestCityForecastByCoordinates(get()) }
    single { DeleteAllForecast(get()) }
    single { SaveForecastCity(get()) }
    single { GetForecastCityFromDatabase(get()) }
    single { GetCityWeatherFromDatabase(get()) }
    single { Dispatchers.Unconfined }
    // get is WeatherRepository, which previously was built in the appModule
//    factory<SaveCityWeather> { get() }
//    factory<RequestWeatherByCoordinates> { get() }
//    factory<DeleteAllCities> { get() }
//    factory<RequestCityForecastByCoordinates> { get() }
//    factory<DeleteAllForecast> { get() }
//    factory<SaveForecastCity> { get() }
//    factory<GetForecastCityFromDatabase> { get() }
//    factory<GetCityWeatherFromDatabase> { get() }
    // single { Dispatchers.Unconfined }

//        // get are WeatherServerSource and WeaterServiceSource
//        scoped { WeatherRepository(get(), get()) }
//        // get is WeatherRepository
//        scoped { GetCityWeatherFromDatabase(get()) }
//        // get is RegionRepository
//        scoped { FindCurrentRegion(get()) }
//        // get is WeatherRepository
//        scoped { SaveCityWeather(get()) }
//        // get is WeatherRepository
//        scoped { RequestWeatherByCoordinates(get()) }
//        // get is WeatherRepository
//        scoped { DeleteAllCities(get()) }
//        // get is WeatherRepository
//        scoped { RequestCityForecastByCoordinates(get()) }
//        // get is WeatherRepository
//        scoped { DeleteAllForecast(get()) }
//        // get is WeatherRepository
//        scoped { SaveForecastCity(get()) }
//        // get is WeatherRepository
//        scoped { GetForecastCityFromDatabase(get()) }

    //}

}

//private val viewModelModule = module {
//
//    viewModel {
//
//        CityViewModel(get())
//
//    }
//
//}
