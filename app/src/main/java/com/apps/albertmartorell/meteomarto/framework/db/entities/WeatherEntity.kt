package com.apps.albertmartorell.meteomarto.framework.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "weather")
data class WeatherEntity(
    //@PrimaryKey(autoGenerate = true) var id: Long = 0,
    var main: String = "",
    var description: String = "",
    var icon: String = ""
    //var dateServerUpdated: Long = 0
)