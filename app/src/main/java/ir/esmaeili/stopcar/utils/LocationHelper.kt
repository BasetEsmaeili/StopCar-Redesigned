package ir.esmaeili.stopcar.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class LocationHelper constructor(private val context: Context) {
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var client: SettingsClient
    private lateinit var settings: LocationSettingsRequest.Builder
    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    /**
     * Initialize LocationHelper Properties
     */
    fun init(
        activity: Activity,
        interval: Long = 600,
        fastestInterval: Long = 500,
        priority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY
    ) {
        locationRequest = LocationRequest.create()
        settings = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        client = LocationServices.getSettingsClient(activity)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        locationRequest.interval = interval
        locationRequest.fastestInterval = fastestInterval
        locationRequest.priority = priority
        return
    }

    /**
     * @return need for request grant permission request on Android M & above
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun isLocationPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            requiredPermissions[0]
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    requiredPermissions[1]
                ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * @param requestCode request code for request the location permissions
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun requestLocationPermissions(activity: Activity, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, requiredPermissions, requestCode)
    }

    /**
     * @return fused location provider last saved location
     */

    fun getLastLocation(): Task<Location> = fusedLocationProviderClient.lastLocation

    /**
     * @param callback callback for handle location changes
     * @return start location updates
     */
    fun requestLocationUpdates(callback: LocationCallback): Task<Void> =
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, callback,
            Looper.getMainLooper()
        )

    /**
     * @param callback remove location update callback
     */

    fun removeLocationUpdates(callback: LocationCallback) {
        fusedLocationProviderClient.removeLocationUpdates(callback)
    }

    /**
     * @return check are Providers Enabled
     */
    fun checkLocationSettings(): Task<LocationSettingsResponse> {
        return client.checkLocationSettings(settings.build())
    }

    /**
     * @return is request permission required
     */
    fun isRequiredPermissionRequest(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }
}