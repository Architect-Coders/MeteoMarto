package com.apps.albertmartorell.meteomarto.testshared

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

fun mockDomainCity(newCoordinates: Coordinates, name: String): City = City(
    newCoordinates,
    mockWeatherList,
    mockMain, 100L,
    mockWind,
    mockClouds,
    mockSys, name
)


fun mockFlowCity(newCoordinates: Coordinates, name: String): Flow<City> = flow {

    City(
        newCoordinates,
        mockWeatherList,
        mockMain, 100L,
        mockWind,
        mockClouds,
        mockSys, name
    )

}
