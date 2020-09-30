package test

import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.domain.cityweather.Coordinates
import albertmartorell.com.usecases.RequestWeatherByCoordinates
import com.apps.albertmartorell.meteomarto.testshared.mockDomainCity
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RequestCityWeatherTest {

    @Mock
    lateinit var weatherRepository: WeatherRepository

    @Mock
    lateinit var getCityFromServer: RequestWeatherByCoordinates

    @Before
    fun setup() {

        getCityFromServer = RequestWeatherByCoordinates(weatherRepository)

    }

    @After
    fun tearDown() {


    }

    @Test
    fun `find out city weather from server`() {

        runBlocking {

            val coordinates = Coordinates(41.54329F, 2.10942F)
            val cityExpected = mockDomainCity(coordinates, "Sabadell")
            whenever(
                weatherRepository.requestWeatherByCoordinates(
                    coordinates.latitude,
                    coordinates.longitude
                )
            ).thenReturn(
                cityExpected
            )

            val cityCollected =
                getCityFromServer.invoke(coordinates.latitude, coordinates.longitude)
            Assert.assertEquals(cityExpected, cityCollected)

        }

    }

}
