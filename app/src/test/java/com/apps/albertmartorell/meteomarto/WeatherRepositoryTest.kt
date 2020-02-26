package com.apps.albertmartorell.meteomarto

import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.domain.responses.City
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
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

            // **** Given
            val localCoordinates = mockCoordinates.copy(10F, 40F)
            val localCity = mockFlowCity(localCoordinates, "Sabadell")
            val givenCity: Flow<City> = localCity

            // **** When
            whenever(deviceSource.isEmpty()).thenReturn(false)
            whenever(deviceSource.getCity()).thenReturn(givenCity)
            val savedCity = weatherRepository.getCityWeatherFromDatabase()

            // **** Then
            assertEquals(localCity, savedCity)

        }

    }

    @Test
    fun `get city weather from server`() {

        runBlocking {

            // **** Given
            val currentCoordinates = mockCoordinates.copy(50F, 4F)
            val givenCity = mockDomainCity(currentCoordinates, "Eibar")

            // **** When
            whenever(
                serverSource.getWeatherByCoordinates(
                    currentCoordinates.latitude,
                    currentCoordinates.longitude
                )
            ).thenReturn(givenCity)

            whenever(
                serverSource.getWeatherByCoordinates(
                    currentCoordinates.latitude, currentCoordinates.longitude
                )
            ).thenReturn(givenCity)
            // **** Then

            todo

        }
    }

}