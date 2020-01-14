package com.apps.albertmartorell.meteomarto.ui.common

import albertmartorell.com.data.sources.LocationDataSource
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Entity that decouples RegionRepository from Google's LocationServices.
 * It returns the current location
 */

class PlayServicesLocationDataSource(application: Application) : LocationDataSource {

    private val geocoder = Geocoder(application)
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    override suspend fun findLastRegion(): String? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result.toRegion())
                }
        }

    private fun Location?.toRegion(): String? {

        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }

        return addresses?.firstOrNull()?.countryCode

    }

}
