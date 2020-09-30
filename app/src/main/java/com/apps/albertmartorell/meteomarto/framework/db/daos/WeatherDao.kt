package com.apps.albertmartorell.meteomarto.framework.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.apps.albertmartorell.meteomarto.framework.db.model.CityEntity
import com.apps.albertmartorell.meteomarto.framework.db.model.ForecastEntity
import kotlinx.coroutines.flow.Flow

/**
 * The class marked with @Dao should either be an interface or an abstract class. At compile time, Room will generate an implementation of this class when it is referenced by a Database.
 *
 *  It is an Interface because the Room compiler generates the source code implementing that interface.
 *  It is not just the Room, Retrofit and other libraries use this pattern too, it is called Programming to an Interface.
 *  Instead of just creating a concrete implementation you just specify the stuff you want to do and they provide you with an implementation that will behave as you requested.
 *
 *  The DAO must be an interface or abstract class because we want to make sure that the CRUD methods we will be creating inside it are implemented at the Class level.
 *  Which is actually the whole idea of having an interface or abstract class.
 */
@Dao
interface WeatherDao {

    @Query("SELECT * FROM cities")
    fun getAll(): List<CityEntity>

    @Query("SELECT * FROM cities LIMIT 1")
    fun getCity(): Flow<CityEntity>

    @Query(value = "SELECT * FROM cities WHERE name = :name")
    fun getCityWeatherByName(name: String): CityEntity

    @Query(value = "SELECT * FROM cities WHERE id = :id")
    fun getCityWeatherById(id: Long): CityEntity

    @Query(value = "SELECT * FROM cities WHERE latitude = :latitude and longitude = :longitude")
    fun getCityWeatherByCoordinates(latitude: Float, longitude: Float): CityEntity

    @Insert(onConflict = REPLACE)
    fun insertWeatherCity(cityWeather: CityEntity)

    @Query("DELETE FROM cities")
    fun deleteAllCities()

    @Query("DELETE FROM forecasts")
    fun deleteAllForecast()

    @Insert(onConflict = REPLACE)
    fun insertForecastCity(forecastEntity: List<ForecastEntity>?)

    @Query("SELECT * FROM forecasts")
    fun getForecastCity(): Flow<List<ForecastEntity>>

}
