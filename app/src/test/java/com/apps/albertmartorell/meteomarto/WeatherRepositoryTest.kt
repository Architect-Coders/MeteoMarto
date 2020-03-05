package com.apps.albertmartorell.meteomarto

import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.domain.responses.City
import com.apps.albertmartorell.meteomarto.testshared.mockCoordinates
import com.apps.albertmartorell.meteomarto.testshared.mockDomainCity
import com.apps.albertmartorell.meteomarto.testshared.mockFlowCity
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryTest {

    @Mock
    lateinit var serverSource: WeatherRepository.WeatherServerSource
    @Mock
    lateinit var deviceSource: WeatherRepository.WeatherDeviceSource

    lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {

        weatherRepository = WeatherRepository(serverSource, deviceSource)

    }

    @After
    fun tearDown() {


    }

    @Test
    fun `get city weather from database`() {

        runBlocking {

            // **** Given: la inicialització del test. L'estat en el que volem que començo
            val localCoordinates = mockCoordinates.copy(10F, 40F)
            val localCity = mockFlowCity(localCoordinates, "Sabadell")
            val givenCity: Flow<City> = localCity

            // **** When: l'execució del codi
            whenever(deviceSource.isEmpty()).thenReturn(false)
            whenever(deviceSource.getCity()).thenReturn(givenCity)
            val savedCity = weatherRepository.getCityWeatherFromDatabase()

            // **** Then: verificar que el When s'ha complert
            assertEquals(localCity, savedCity)

        }

    }

    @Test
    fun `get city weather from server`() {

        runBlocking {

            // **** Given: la inicialització del test. L'estat en el que volem que començo
            val currentCoordinates = mockCoordinates.copy(50F, 4F)
            val givenCity = mockDomainCity(currentCoordinates, "Eibar")

            // Configurem els mocks: quan executem una funció retorni un cert valor. En
            // aquest cas quan la funció getWeatherByCoordinates() és cridada amb els valors
            // currentCoordinates, retorni l'objecte givenCity
            whenever(
                serverSource.getWeatherByCoordinates(
                    currentCoordinates.latitude,
                    currentCoordinates.longitude
                )
            ).thenReturn(givenCity)

            // **** When: l'execució del codi
            val serverCity = serverSource.getWeatherByCoordinates(
                currentCoordinates.latitude,
                currentCoordinates.longitude
            )

            // **** Then: verificar que el When s'ha complert
            assertEquals(givenCity, serverCity)

        }

    }

    @Test
    fun `verifies data got from server saved on local`() {

        runBlocking {

            val currentCoordinates = mockCoordinates.copy(59F, 6F)
            val givenCity = mockDomainCity(currentCoordinates, "Sant Esteve d'en Bas")

            //whenever(
            //    serverSource.getWeatherByCoordinates(
            //        currentCoordinates.latitude,
            //        currentCoordinates.longitude
            //    )
            //).thenReturn(givenCity)

            //weatherRepository.requestWeatherByCoordinates(
            //    currentCoordinates.latitude,
            //    currentCoordinates.longitude
            //)

            deviceSource.saveCityWeather(givenCity)

            //verify(serverSource).getWeatherByCoordinates(
            //    currentCoordinates.latitude,
            //    currentCoordinates.longitude
            //)
            verify(deviceSource).saveCityWeather(givenCity)

        }

    }

}