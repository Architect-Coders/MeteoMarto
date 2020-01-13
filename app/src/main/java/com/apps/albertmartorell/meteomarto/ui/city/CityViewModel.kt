package com.apps.albertmartorell.meteomarto.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apps.albertmartorell.meteomarto.ui.Scope
import com.apps.albertmartorell.meteomarto.ui.common.Event
import com.apps.albertmartorell.meteomarto.ui.model.WeatherRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CityViewModel(private val weatherRepository: WeatherRepository) : ViewModel(), Scope {

    override lateinit var job: Job

//    sealed class UiModel {
//
//        object Loading : UiModel()
//        object RequestLocationPermission : UiModel()
//
//        object PermissionGranted : UiModel() // location permission is granted
//
//        object PermissionDenied : UiModel() // location permission is denied
//
//        class Success(val cityEntity: String) : UiModel()
//         //As it has state, it must be a class
//        class Finished(val cityEntity: String) : UiModel()
//
//        object cityWeatherCollected : UiModel() // the city weather has been already collected
//        object NotInternet : UiModel() // there is not Internet connection
//
//    }


    private val _eventRequestLocationPermission = MutableLiveData<Event<Unit>>()
    val eventRequestLocationPermission: LiveData<Event<Unit>>
        get() {

            // the first time somebody subscribes to the LiveData
            if (_eventRequestLocationPermission.value == null) {

                refresh()

            }

            return _eventRequestLocationPermission

        }

    private val _eventPermissionDenied = MutableLiveData<Event<Unit>>()
    val eventPermissionDenied: LiveData<Event<Unit>> = _eventPermissionDenied

    private val _eventPerMissionGranted = MutableLiveData<Event<Unit>>()
    val eventPermissionGranted: LiveData<Event<Unit>> = _eventPerMissionGranted

    private val _eventRequestedLocationPermissionFinished = MutableLiveData<Event<String>>()
    val eventRequestedLocationPermissionFinished = _eventRequestedLocationPermissionFinished

    init {

        initScope()

    }

    /**
     * I wonder when to call this method: a good moment is wait when somebody is subscribed to the LiveData for the first time. As alternative it could be called in the init constructor
     */
    private fun refresh() {

        _eventRequestLocationPermission.value = Event(Unit)

    }

    fun onCoarsePermissionRequested(success: Boolean) {

        launch {

            if (success) _eventPerMissionGranted.value = Event(Unit)
            else _eventPermissionDenied.value = Event(Unit)

        }

    }

    fun getCityWeather() {

        launch {

            _eventRequestedLocationPermissionFinished.value =
                Event(weatherRepository.getCityWeather())

        }

    }

    override fun onCleared() {

        cancelScope()

    }

    /**
     * As CityViewModel has arguments, we need to create it with a factory, else Android will create a ViewModel with empty constructor, and CityViewModel needs arguments
     */
    @Suppress("UNCHECKED_CAST")
    class CityViewModelFactory(private val weatherRepository: WeatherRepository) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T =

            CityViewModel(weatherRepository) as T

    }

}