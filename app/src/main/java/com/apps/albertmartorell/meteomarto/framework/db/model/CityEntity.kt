package com.apps.albertmartorell.meteomarto.framework.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val latitude: Float? = 0F,
    val longitude: Float? = 0F,
    val main: String? = "",
    val description: String? = "",
    val icon: String? = "",
    val temperature: Float? = 0F,
    val humidity: Float? = 0F,
    val pressure: Float? = 0F,
    val temperatureMin: Float? = 0F,
    val temperatureMax: Float? = 0F,
    val visibility: Long? = 0,
    val speed: Float? = 0F,
    val degrees: Float? = 0F,
    val coverage: Float? = 0F,
    val type: Int? = 0,
    val message: Float? = 0F,
    val country: String? = "",
    val sunrise: Long? = 0,
    val sunset: Long? = 0,
    val name: String? = "",
    val dateServerUpdated: Date?
)