package com.amonteiro.a2023_07_aston_2.utils

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat

object LocationUtils {


    fun getLastKnownLocation(c: Context): Location? {

        //Contrôle de la permission
        if (ContextCompat.checkSelfPermission(c, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_DENIED) {
            return null
        }

        var lm = c.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //on teste chaque provider(réseau, GPS...) et
        //on garde la localisation avec la meilleurs précision
        return lm.getProviders(true).map { lm.getLastKnownLocation(it) }
            .minByOrNull { it?.accuracy ?: Float.MAX_VALUE }
    }


}