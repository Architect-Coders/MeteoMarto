package com.apps.albertmartorell.meteomarto.ui.city

import albertmartorell.com.usecases.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.apps.albertmartorell.meteomarto.R
import com.apps.albertmartorell.meteomarto.databinding.LytActLandingBinding
import com.apps.albertmartorell.meteomarto.framework.Interactors
import org.koin.androidx.scope.currentScope

class Landing : AppCompatActivity() {

    private lateinit var viewModel: CityViewModel
    private lateinit var binding: LytActLandingBinding
    private val getCityWeatherFromDatabase: GetCityWeatherFromDatabase by currentScope.inject()
    private val findCurrentRegion: FindCurrentRegion by currentScope.inject()
    private val saveCityWeather: SaveCityWeather by currentScope.inject()
    private val requestCityWeatherByCoordinates: RequestWeatherByCoordinates by currentScope.inject()
    private val deleteAllCities: DeleteAllCities by currentScope.inject()
    private val requestCityForecastByCoordinates: RequestCityForecastByCoordinates by currentScope.inject()
    private val deleteAllForecast: DeleteAllForecast by currentScope.inject()
    private val saveForecastCity: SaveForecastCity by currentScope.inject()
    private val getForecastCityFromDatabase: GetForecastCityFromDatabase by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // the this param does that each time we access the view model providers checks if this view model already exists: if not it is created else it is got again
        viewModel = ViewModelProviders.of(
            this, CityViewModel.CityViewModelFactory(
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