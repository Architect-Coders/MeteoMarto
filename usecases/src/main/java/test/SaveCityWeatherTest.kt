package test

import albertmartorell.com.data.repositories.WeatherRepository
import albertmartorell.com.domain.cityweather.Coordinates
import albertmartorell.com.usecases.SaveCityWeather
import com.apps.albertmartorell.meteomarto.testshared.mockDomainCity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SaveCityWeatherTest {

    @Mock
    lateinit var weatherRepository: WeatherRepository

    @Mock
    lateinit var saveCityWeather: SaveCityWeather


    @Before
    fun setup() {

        saveCityWeather = SaveCityWeather(weatherRepository)

    }

    @After
    fun tearDown() {


    }

    @Test
    fun `save city weather`() {

        runBlocking {

            val coordinates = Coordinates(41.54329F, 2.10942F)
            val cityExpected = mockDomainCity(coordinates, "Sabadell")
            saveCityWeather.invoke(cityExpected)
            Mockito.verify(weatherRepository).saveCityWeather(cityExpected)

        }

    }

}
