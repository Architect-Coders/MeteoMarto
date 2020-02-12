package com.apps.albertmartorell.meteomarto

import android.app.Application
import androidx.room.Room
import com.apps.albertmartorell.meteomarto.framework.db.MeteoMartoDatabase
import com.apps.albertmartorell.meteomarto.ui.initDI

class MeteoMartoApp : Application() {

    // It is marked with "late-init" to crash if it is not initialized before use
    lateinit var meteoMartoDatabase: MeteoMartoDatabase

    override fun onCreate() {

        super.onCreate()

        meteoMartoDatabase =
            Room.databaseBuilder(this, MeteoMartoDatabase::class.java, "MeteoMartoDatabase").build()
        initDI()

    }

}