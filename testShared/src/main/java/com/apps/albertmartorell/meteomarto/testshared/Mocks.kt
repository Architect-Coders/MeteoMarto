package com.apps.albertmartorell.meteomarto.testshared

/**
 * En aquest mòdul posem tot el contingut que necessitem per compartir entre els diferents tests.
 *
 */
import albertmartorell.com.domain.cityforecast.ForecastDomain
import albertmartorell.com.domain.cityweather.*
import albertmartorell.com.domain.responses.CityDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val mockCoordinates = Coordinates(0F, 0F)
val mockWeather = Weather("main", "desription", "icon")
val mockWeatherList = listOf(mockWeather)
val mockMain = Main(
    20F, 75F, 1010F, 10F,
    25F, 17F
)
val mockWind = Wind(60F, 25F)
val mockClouds = Clouds(85F)
val mockSys = Sys(4, 789F, "Belgium", 89, 54)

fun mockDomainCity(newCoordinates: Coordinates, name: String): CityDomain = CityDomain(
    newCoordinates,
    mockWeatherList,
    mockMain, 100L,
    mockWind,
    mockClouds,
    mockSys, name
)

fun mockFlowCity(newCoordinates: Coordinates, name: String): Flow<CityDomain> = flow {

    CityDomain(
        newCoordinates,
        mockWeatherList,
        mockMain, 100L,
        mockWind,
        mockClouds,
        mockSys, name
    )

}

fun mockForecastDomain(): ForecastDomain = ForecastDomain(

    "",
    10,
    20,
    15,
    "Temps fake",
    ""
)
