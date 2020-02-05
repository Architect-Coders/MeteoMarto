package com.apps.albertmartorell.meteomarto.ui.city

import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.usecases.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.apps.albertmartorell.meteomarto.R
import com.apps.albertmartorell.meteomarto.databinding.LytActLandingBinding
import com.apps.albertmartorell.meteomarto.framework.Interactors
import com.apps.albertmartorell.meteomarto.ui.city.CityViewModel.CityViewModelFactory
import org.koin.androidx.scope.currentScope

class Landing : AppCompatActivity() {

    private lateinit var viewModel: CityViewModel
    private lateinit var binding: LytActLandingBinding
    private val weatherRepository: WeatherRepository by currentScope.inject()
    private val getCityWeatherFromDatabase: GetCityWeatherFromDatabase by currentScope.inject()
    private val findCurrentRegion: FindCurrentRegion by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

//        val weatherRepository =
//            WeatherRepository(ImpWeatherServerSource(), ImpWeatherDeviceSource(applicationContext))

        //val weatherRepository:WeatherRepository by weatherRepositoryModule()

        //val getCityWeatherFromDatabase = GetCityWeatherFromDatabase(weatherRepository)

//        val findCurrentRegion = FindCurrentRegion(
//            RegionRepository(
//                PlayServicesLocationDataSource(
//                    app
//                ),
//                AndroidPermissionChecker(
//                    app
//                )
//            )
//        )

        val saveCityWeather = SaveCityWeather(weatherRepository)
        val requestCityWeatherByCoordinates = RequestWeatherByCoordinates(weatherRepository)
        val deleteAllCities = DeleteAllCities(weatherRepository)
        val requestCityForecastByCoordinates = RequestCityForecastByCoordinates(weatherRepository)
        val deleteAllForecast = DeleteAllForecast(weatherRepository)
        val saveForecastCity = SaveForecastCity(weatherRepository)
        val getForecastCityFromDatabase = GetForecastCityFromDatabase(weatherRepository)

        // the this param does that each time we access the view model providers checks if this view model already exists: if not it is created else it is got again
        viewModel = ViewModelProviders.of(
            this, CityViewModelFactory(
                Interactors(
                    findCurrentRegion,
                    getCityWeatherFromDatabase,
                    requestCityWeatherByCoordinates,
                    saveCityWeather,
                    deleteAllCities,
                    requestCityForecastByCoordinates,
                    deleteAllForecast,
                    saveForecastCity,
                    getForecastCityFromDatabase
                )
            )
        )[CityViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.lyt_act_landing)

        binding.lifecycleOwner = this
        customizeToolbar()

    }

    private fun customizeToolbar() {

        setSupportActionBar(binding.toolbar)

    }

}