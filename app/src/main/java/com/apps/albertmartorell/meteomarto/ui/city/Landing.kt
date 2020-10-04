package com.apps.albertmartorell.meteomarto.ui.city

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.apps.albertmartorell.meteomarto.R
import com.apps.albertmartorell.meteomarto.databinding.LytActLandingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class Landing : AppCompatActivity() {




    private lateinit var binding: LytActLandingBinding
//    private val getCityWeatherFromDatabase: GetCityWeatherFromDatabase by inject()
//    private val findCurrentRegion: FindCurrentRegion by inject()
//    private val saveCityWeather: SaveCityWeather by inject()
//    private val requestCityWeatherByCoordinates: RequestWeatherByCoordinates by inject()
//    private val deleteAllCities: DeleteAllCities by inject()
//    private val requestCityForecastByCoordinates: RequestCityForecastByCoordinates by inject()
//    private val deleteAllForecast: DeleteAllForecast by inject()
//    private val saveForecastCity: SaveForecastCity by inject()
//    private val getForecastCityFromDatabase: GetForecastCityFromDatabase by inject()
    val viewModel: CityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

//        // the this param does that each time we access the view model providers checks if this view model already exists: if not it is created else it is got again
//        viewModel = ViewModelProviders.of(
//            this, CityViewModel.CityViewModelFactory(
//                Interactors(
//                    findCurrentRegion,
//                    getCityWeatherFromDatabase,
//                    requestCityWeatherByCoordinates,
//                    saveCityWeather,
//                    deleteAllCities,
//                    requestCityForecastByCoordinates,
//                    deleteAllForecast,
//                    saveForecastCity,
//                    getForecastCityFromDatabase
//                ), Dispatchers.Main
//            )
//        )[CityViewModel::class.java]

        binding = DataBindingUtil.setContentView(this, R.layout.lyt_act_landing)
        binding.lifecycleOwner = this
        customizeToolbar()

    }

    private fun customizeToolbar() {

        setSupportActionBar(binding.toolbar)

    }

}
