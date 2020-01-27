package com.apps.albertmartorell.meteomarto.ui.city

import albertmartorell.com.data.repositories.RegionRepository
import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.usecases.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.apps.albertmartorell.meteomarto.R
import com.apps.albertmartorell.meteomarto.databinding.LytActLandingBinding
import com.apps.albertmartorell.meteomarto.framework.Interactors
import com.apps.albertmartorell.meteomarto.framework.db.ImpWeatherDeviceSource
import com.apps.albertmartorell.meteomarto.framework.server.ImpWeatherServerSource
import com.apps.albertmartorell.meteomarto.ui.app
import com.apps.albertmartorell.meteomarto.ui.city.CityViewModel.CityViewModelFactory
import com.apps.albertmartorell.meteomarto.ui.common.AndroidPermissionChecker
import com.apps.albertmartorell.meteomarto.ui.common.PlayServicesLocationDataSource

class Landing : AppCompatActivity() {

    private lateinit var viewModel: CityViewModel
    private lateinit var binding: LytActLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val weatherRepository =
            WeatherRepository(ImpWeatherServerSource(), ImpWeatherDeviceSource(applicationContext))
        val getCityWeatherFromDatabase = GetCityWeatherFromDatabase(weatherRepository)

        val findCurrentRegion = FindCurrentRegion(
            RegionRepository(
                PlayServicesLocationDataSource(
                    app
                ),
                AndroidPermissionChecker(
                    app
                )
            )
        )

        val saveCityWeather = SaveCityWeather(weatherRepository)
        val requestCityWeatherByCoordinates = RequestWeatherByCoordinates(weatherRepository)
        val deleteAllCities = DeleteAllCities(weatherRepository)

        // the this param does that each time we access the view model providers checks if this view model already exists: if not it is created else it is got again
        viewModel = ViewModelProviders.of(
            this, CityViewModelFactory(
                Interactors(
                    findCurrentRegion,
                    getCityWeatherFromDatabase,
                    requestCityWeatherByCoordinates,
                    saveCityWeather,
                    deleteAllCities
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