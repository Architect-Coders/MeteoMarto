package com.apps.albertmartorell.meteomarto.ui.common

import albertmartorell.com.data.sources.LocationDataSource
import albertmartorell.com.domain.cityweather.Coordinates
import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Entity that decouples RegionRepository from Google's LocationServices.
 * It returns the current location
 */

class PlayServicesLocationDataSource(application: Application) : LocationDataSource {

    //private val geocoder = Geocoder(application)
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    /**

    override suspend fun findLastRegion(): String? =
    suspendCancellableCoroutine { continuation ->
    fusedLocationClient.lastLocation
    .addOnCompleteListener {
    continuation.resume(it.result.toRegion())
    }
    }
     **/

    @SuppressLint("MissingPermission")
    override suspend fun findLastRegion(): Coordinates =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result.getCoordinate())
                }
        }


    private fun Location?.getCoordinate(): Coordinates {

        var coordinates =
            Coordinates(0F, 0F)

        this?.let {

            coordinates = Coordinates(
                latitude.toFloat(),
                longitude.toFloat()
            )

        }

        return coordinates

    }

    /**
    private fun Location?.getCoordinate(): String {

    val addresses = this?.let {

    geocoder.getFromLocation(latitude, longitude, 1)
    }

    val coordinates =
    addresses?.firstOrNull()?.latitude.toString() + "-" + addresses?.firstOrNull()?.longitude.toString()
    return coordinates

    }

     **/
    /**
    private fun Location?.toRegion(): String? {

    val addresses = this?.let {

    geocoder.getFromLocation(latitude, longitude, 1)
    }

    return addresses?.firstOrNull()?.latitude.toString() +
    addresses?.firstOrNull()?.longitude.toString()

    }
     **/

}
