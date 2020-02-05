package com.apps.albertmartorell.meteomarto.ui

import albertmartorell.com.data.repositories.PermissionChecker
import albertmartorell.com.data.repositories.RegionRepository
import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.data.sources.LocationDataSource
import albertmartorell.com.usecases.FindCurrentRegion
import albertmartorell.com.usecases.GetCityWeatherFromDatabase
import android.app.Application
import com.apps.albertmartorell.meteomarto.framework.db.ImpWeatherDeviceSource
import com.apps.albertmartorell.meteomarto.framework.server.ImpWeatherServerSource
import com.apps.albertmartorell.meteomarto.ui.city.CityViewModel
import com.apps.albertmartorell.meteomarto.ui.city.Landing
import com.apps.albertmartorell.meteomarto.ui.common.AndroidPermissionChecker
import com.apps.albertmartorell.meteomarto.ui.common.PlayServicesLocationDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {

    // to create the dependencies graph
    startKoin {

        // log
        androidLogger()
        // the android context to used in these modules. It is an extra dependency
        androidContext(this@initDI)
        // the modules to load
        modules(listOf(appModule, dataModule, useCasesModule, scopesModule))

    }

}

// the name of the module belogns to the application module: app, usecases, data, domain....
// the dependencies that its has, are the classes that we initialize in this module
private val appModule = module {

    factory<WeatherRepository.WeatherServerSource> { ImpWeatherServerSource() }
    factory<WeatherRepository.WeatherDeviceSource> { ImpWeatherDeviceSource(androidApplication()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(androidApplication()) }
    factory<PermissionChecker> { AndroidPermissionChecker(androidApplication()) }

}

private val dataModule = module {

    // get is LocationDataSource and PermissionChecker, which previously are built in the appModule
    factory { RegionRepository(get(), get()) }

}

private val useCasesModule = module {

    // get is RegionRepository,which previously is built in the dataModule
    factory<FindCurrentRegion> { get() }
}

private val scopesModule = module {

    // these dependencies can only be used in the Landing activity
    scope(named<Landing>()) {

        // get are WeatherServerSource and WeaterServiceSource
        scoped { WeatherRepository(get(), get()) }
        // get is WeatherRepository
        scoped { GetCityWeatherFromDatabase(get()) }
        // get is RegionRepository
        scoped { FindCurrentRegion(get()) }

    }

}

val viewModelModule = module {

    viewModel {
        CityViewModel(get())
    }
}