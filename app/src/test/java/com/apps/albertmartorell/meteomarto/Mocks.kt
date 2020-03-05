package com.apps.albertmartorell.meteomarto

import albertmartorell.com.domain.cityweather.*
import albertmartorell.com.domain.responses.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val mockCoordinates = Coordinates(0F, 0F)
val mockWeather = Weather("main", "desription", "icon")
val mockWeatherList = listOf<Weather>(mockWeather)
val mockMain = Main(
    20F, 75F, 1010F, 10F,
    25F, 17F
)
val mockWind = Wind(60F, 25F)
val mockClouds = Clouds(85F)
val mockSys = Sys(4, 789F, "Belgium", 89, 54)

//val localCity: Flow<City> = mockCity.copy(localCoordinates)

/**

fun testFlow(){

lateinit var kk: Flow<City>
kk = Flow<City>(mockCoordinates, mockWeatherList, mockMain, 100L,
mockWind, mockClouds, mockSys, "Mechelen")

//var queue: Queue<Int> = ArrayDeque<Int>()

var hola:Flow<City> = TestForFlow()

}

 */


/*
class TestForFlow:Flow<City>{

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<City>) {



    }

    fun createCity():Flow<City>{

        return Flow<City>(mockCoordinates, mockWeatherList, mockMain, 100L,
            mockWind, mockClouds, mockSys, "Mechelen")

    }
}
*/

//val mockDomainCity () = City(
//mockCoordinates, mockWeatherList, mockMain, 100L,
//mockWind, mockClouds, mockSys, "Mechelen"
//)

fun mockDomainCity(newCoordinates: Coordinates, name: String): City = City(
    newCoordinates, mockWeatherList, mockMain, 100L,
    mockWind, mockClouds, mockSys, name
)


fun mockFlowCity(newCoordinates: Coordinates, name: String): Flow<City> = flow {

    City(
        newCoordinates, mockWeatherList, mockMain, 100L,
        mockWind, mockClouds, mockSys, name
    )

    // flow builder
    //for (i in 1..3) {
    //    delay(100) // pretend we are doing something useful here
    //    emit(i) // emit next value
    //}
}