package ir.esmaeili.stopcar.utils

import android.content.Context
import android.graphics.Bitmap
import android.location.Location
import android.os.Handler
import android.os.RemoteException
import androidx.annotation.IdRes
import androidx.annotation.RawRes
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers

class MapHelper constructor(private val context: Context) {

    private lateinit var googleMap: GoogleMap
    private var isMapReady = false
    private lateinit var mapFragment: StopCarMapFragment

    /**
     * @param container containerID for add SupportMapFragment
     * @param fragmentManager fragmentMangerInstance for add SupportMapFragment
     * @param callback mapFragment Events
     */
    fun init(
        @IdRes container: Int,
        fragmentManager: FragmentManager,
        callback: MapUtilsCallback
    ) {
        callback.onStartInitializeMap()
        Handler().postDelayed({
            Thread {
                mapFragment = StopCarMapFragment.newInstance()
                fragmentManager.beginTransaction().add(container, mapFragment).commit();
                Completable.create {
                    mapFragment.getMapAsync { googleMap ->
                        googleMap.apply {
                            this@MapHelper.googleMap = googleMap
                            isMapReady = true
                            callback.onMapReady(
                                googleMap
                            )
                        }
                    }
                }.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }.start()
        }, Constants.INIT_MAP_DELAY)
    }

    /**
     * @return is SupportMap Initialized  and add to View
     */

    fun isMapReady() = isMapReady


    /**
     * @return googleMap object
     * @throws UninitializedPropertyAccessException google style object has not initialized
     */
    fun getGoogleMap(): GoogleMap {
        try {
            return googleMap
        } catch (e: UninitializedPropertyAccessException) {
            throw e
        }
    }

    /**
     * @return support map fragment instancel
     * @throws  UninitializedPropertyAccessException variable is not initialized
     */
    fun getMapFragment(): StopCarMapFragment {
        try {
            return mapFragment
        } catch (e: UninitializedPropertyAccessException) {
            throw e
        }
    }

    /**
     * @param style   a json for set night theme on  style
     * @throws RemoteException error in set style on style
     */
    fun setMapStyle(@RawRes style: Int) {
        try {
            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, style))
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * @param adapter a generic type from GoogleMap.InfoWindowAdapter for set custom info window adapter
     */

    fun <Adapter : GoogleMap.InfoWindowAdapter> setInfoWindowAdapter(adapter: Adapter) {
        if (isMapReady)
            googleMap.setInfoWindowAdapter(adapter)
        else
            throw UninitializedGoogleMapException()
    }

    /**
     * @param position latitude and longitude of place to add marker
     * @param title string text for show in head of marker
     * @param icon  bitmap for show on style
     */
    fun addMarker(position: LatLng, title: String, icon: Bitmap) {
        if (isMapReady)
            googleMap.addMarker(
                MarkerOptions().position(position).title(title)
                    .icon(BitmapDescriptorFactory.fromBitmap(icon)).draggable(false)
            )
        else
            throw UninitializedGoogleMapException()
    }

    /**
     * @param position latitude and longitude of place to add marker
     * @param icon  bitmap for show on style
     */
    fun addMarker(position: LatLng, icon: Bitmap) {
        googleMap.addMarker(
            MarkerOptions().position(position)
                .icon(BitmapDescriptorFactory.fromBitmap(icon)).draggable(false)
        )
    }

    /**
     * @param position latitude and longitude of place for move the style screen to there
     * @param zoomLevel level of show detail on style
     * @param isAnimate animating the style when move
     */
    fun moveCamera(position: LatLng, zoomLevel: Float, isAnimate: Boolean) {
        if (isMapReady)
            if (isAnimate) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, zoomLevel))
            } else {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoomLevel))
            }
        else
            throw UninitializedGoogleMapException()
    }

    /**
     * @param location latitude and longitude of place for move the style screen to there
     * @param zoomLevel level of show detail on style
     * @param isAnimate animating the style when move
     */
    fun moveCamera(location: Location, zoomLevel: Float, isAnimate: Boolean) {
        if (isMapReady)
            if (isAnimate) {
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            location.latitude,
                            location.longitude
                        ), zoomLevel
                    )
                )
            } else {
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            location.latitude,
                            location.longitude
                        ), zoomLevel
                    )
                )
            }
        else
            throw UninitializedGoogleMapException()
    }

    /**
     * @param value enable or disable show user location on style
     */

    fun setMyLocationEnabled(value: Boolean) {
        if (isMapReady)
            googleMap.isMyLocationEnabled = value
        else
            throw UninitializedGoogleMapException()
    }

    /**
     * @return googleMap ui settings
     */

    fun getUiSettings(): UiSettings {
        if (isMapReady) {
            return googleMap.uiSettings
        } else {
            throw UninitializedGoogleMapException()
        }
    }

    /**
     * @return center style latitude and longitude
     */
    fun getLocation(): LatLng {
        return if (isMapReady) {
            googleMap.cameraPosition.target
        } else {
            Constants.TEHRAN_LATLNG
        }
    }

    /**
     * @param value enable or disable show traffic on style
     */
    fun setTrafficEnabled(value: Boolean) {
        if (isMapReady)
            googleMap.isTrafficEnabled = value
        else
            throw UninitializedGoogleMapException()
    }
}