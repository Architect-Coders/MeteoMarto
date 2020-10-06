package com.apps.albertmartorell.meteomarto

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.apps.albertmartorell.defaultFakeCoordinates
import com.apps.albertmartorell.initMockedDi
import com.apps.albertmartorell.meteomarto.ui.city.CityViewModel
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

// AutoCloseKoinTest: la classe abstract implemententa la interface KoinTest. El mètode stopKoin() ja està implmentat i per tant no cal fer-ho cada vegada.
@RunWith(MockitoJUnitRunner::class)
class CityWeatherIntegrationTests : AutoCloseKoinTest() {

    @ExperimentalCoroutinesApi
    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule() // Force LiveData to be executed the thread test

    @Mock
    lateinit var observer: Observer<CityViewModel.UiForecastModel>

    private lateinit var cityViewModel: CityViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {

        Dispatchers.setMain(dispatcher)

        // Per inicialitzar el grafo de dependencies. En aquí però el mòdul de scopes no el podem fer servir ja que no tenim activities.
        // Inicialitzem els mòduls que són específics per testing i que són diferents que el codi real:
        val vmModules = module {

            factory { CityViewModel(get(), get()) }

        }

        initMockedDi(vmModules)
        cityViewModel = get()

    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `forecast is not available from server when location is wrong`() {

        cityViewModel.eventRequestForecast.observeForever(observer)
        verify(observer).onChanged(CityViewModel.UiForecastModel.Loading)

        runBlocking {

            cityViewModel.startRequestForecast(
                defaultFakeCoordinates.latitude,
                defaultFakeCoordinates.longitude, dispatcher
            )

        }

        verify(observer).onChanged(
            CityViewModel.UiForecastModel.SuccessRequest(emptyList())
        )

    }

}
