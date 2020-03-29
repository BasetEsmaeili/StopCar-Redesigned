package ir.esmaeili.stopcar.utils

import com.google.android.gms.maps.GoogleMap

interface MapUtilsCallback {
    fun onStartInitializeMap()
    fun onMapReady(googleMap: GoogleMap)
}