package ir.esmaeili.stopcar.ui.fragments.park

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.databinding.FragmentNewParkBinding
import ir.esmaeili.stopcar.models.*
import ir.esmaeili.stopcar.ui.base.BaseFragment
import ir.esmaeili.stopcar.utils.*
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import javax.inject.Inject

class NewParkFragment : BaseFragment<FragmentNewParkBinding, NewParkViewModel>(),
    OnMapReadyCallback, MapUtilsCallback, OnTouchListener {
    companion object {
        const val TAG: String = "FragmentNewPark"
    }

    private lateinit var mViewModel: NewParkViewModel
    lateinit var mapHelper: MapHelper
        @Inject set
    lateinit var persianDate: PersianDate
        @Inject set
    lateinit var persianDateFormat: PersianDateFormat
        @Inject set

    lateinit var factory: NewParkViewModelProviderFactory
        @Inject set

    lateinit var utils: Utils
        @Inject set

    lateinit var mContext: Context
        @Inject set

    lateinit var locationHelper: LocationHelper
        @Inject set

    override fun getLayoutId(): Int {
        return R.layout.fragment_new_park
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): NewParkViewModel {
        mViewModel = ViewModelProvider(
            this,
            factory
        ).get(NewParkViewModel::class.java)
        return mViewModel
    }

    override fun onViewCreated(p0: View, savedInstanceState: Bundle?) {
        super.onViewCreated(p0, savedInstanceState)
        viewDataBinding.viewModel = mViewModel

        if (!mViewModel.isMapFragmentAttached()) {
            locationHelper.init(baseActivity)
            mapHelper.init(R.id.container_park_map, childFragmentManager, this)
            mViewModel.setMapFragmentAttached()
        }
        if (mViewModel.isMapFragmentAttached() && mapHelper.isMapReady()) {
            showExtraViews()
        }
        initObservers()
    }


    private fun initObservers() {
        mViewModel.getLocationButtonState().observe(viewLifecycleOwner, Observer {
            if (it) {
                updateLocationButton(true)
            } else {
                updateLocationButton(false)
            }
        })
        mViewModel.getReverseGeoCoderAddress().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Response.None -> {
                    viewDataBinding.toolbarNewPark.title = resources.getString(R.string.app_name)
                    viewDataBinding.progressbarParkGeo.visibility = View.GONE
                }
                is Response.Loading -> {
                    viewDataBinding.progressbarParkGeo.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    if (it.data.status == Constants.NESHAN_RESULT_SUCCESS)
                        if (it.data.formattedAddress.contains(utils.getString(R.string.label_address_state))) {
                            viewDataBinding.toolbarNewPark.title =
                                it.data.formattedAddress.let { address ->
                                    address.removeRange(0, address.indexOf('،', 4).plus(2))
                                }
                        } else
                            viewDataBinding.toolbarNewPark.title =
                                resources.getString(R.string.label_error_find_address)
                    viewDataBinding.progressbarParkGeo.visibility = View.GONE
                }
                is Response.Error -> {
                    viewDataBinding.toolbarNewPark.title =
                        resources.getString(R.string.label_error_find_address)
                    viewDataBinding.progressbarParkGeo.visibility = View.GONE
                }
            }
        })

        mViewModel.getHistory().observe(viewLifecycleOwner, Observer {
            if (!it.hasBeenHandled)
                when (it.getContentIfNotHandled()) {
                    is Response.Loading -> {
                        sharedViewModel.setProgressbar(true)
                    }
                    is Response.Success -> {
                        sharedViewModel.setProgressbar(false)
                        sharedViewModel.setAlertEvent(
                            AlertView.CustomToast(
                                EventType.SUCCESS,
                                utils.getString(R.string.label_success_title),
                                utils.getString(R.string.label_success_description_car_history),
                                R.drawable.ic_done_gray_24dp
                            )
                        )
                    }
                    is Response.Error -> {
                        sharedViewModel.setProgressbar(false)
                        sharedViewModel.setAlertEvent(
                            AlertView.CustomToast(
                                EventType.SUCCESS,
                                utils.getString(R.string.label_error_title),
                                utils.getString(R.string.label_error_description_car_save_history),
                                R.drawable.ic_done_gray_24dp
                            )
                        )
                    }
                }
        })

        mViewModel.getEventHandler().observe(viewLifecycleOwner, Observer {
            if (!it.hasBeenHandled)
                when (it.getContentIfNotHandled()) {
                    Constants.EVENT_GET_LOCATION -> {
                        getLastKnowLocation()
                    }
                    Constants.EVENT_SAVE_PARK -> {
                        showSelectCarDialogFragment()
                    }
                }
        })

        sharedViewModel.selectedCar.observe(viewLifecycleOwner, Observer {
            if (!it.hasBeenHandled)
                saveParkLocation(it.getContentIfNotHandled()!!)
        })
        sharedViewModel.permissionEvent.observe(viewLifecycleOwner, Observer { permission ->
            if (permission.requestCode == Constants.LOCATION_PERMISSION_REQUEST_CODE && permission.grantResults.isNotEmpty() && permission.grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnowLocation()
            } else {
                sharedViewModel.setAlertEvent(
                    AlertView.CustomToast(
                        EventType.ERROR,
                        utils.getString(R.string.label_error_title),
                        utils.getString(R.string.label_error_location_permission),
                        R.drawable.ic_error_white_24dp
                    )
                )
            }
        })
        sharedViewModel.activityResultEvent.observe(viewLifecycleOwner, Observer { result ->
            if (result.requestCode == Constants.LOCATION_DIALOG_REQUEST_CODE && result.resultCode == Activity.RESULT_OK) {
                getLastKnowLocation()
            } else {
                mViewModel.setLocationButtonState(false)
                sharedViewModel.setAlertEvent(
                    AlertView.CustomToast(
                        EventType.ERROR,
                        utils.getString(R.string.label_error_title),
                        utils.getString(R.string.label_error_find_user_location),
                        R.drawable.ic_error_white_24dp
                    )
                )
            }
        })
    }

    private fun showSelectCarDialogFragment() {
        findNavController().navigate(R.id.action_fragmentNewPark_to_saveCarDialogFragment)
    }


    private fun updateLocationButton(state: Boolean) {
        if (state) {
            if (isNightMode) {
                viewDataBinding.floatParkLocation.colorFilter =
                    PorterDuffColorFilter(
                        utils.getColor(R.color.color_white),
                        PorterDuff.Mode.SRC_ATOP
                    )
            } else {
                viewDataBinding.floatParkLocation.colorFilter = PorterDuffColorFilter(
                    utils.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP
                )
            }


        } else {
            if (isNightMode) {
                viewDataBinding.floatParkLocation.colorFilter =
                    PorterDuffColorFilter(
                        utils.getColor(R.color.color_location_none),
                        PorterDuff.Mode.SRC_ATOP
                    )
            } else {
                viewDataBinding.floatParkLocation.colorFilter = PorterDuffColorFilter(
                    utils.getColor(R.color.colorMain),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        }
    }

    private fun showExtraViews() {
        viewDataBinding.cardAddressView.visibility = View.VISIBLE
        viewDataBinding.btnParkSave.visibility = View.VISIBLE
        viewDataBinding.floatParkLocation.visibility = View.VISIBLE
        viewDataBinding.imgNewParkMarker.visibility = View.VISIBLE
    }

    private fun saveParkLocation(car: Car) {
        val history = History()
        history.historyID = 0
        history.carID = car.carID
        history.latitude = mapHelper.getLocation().latitude
        history.longitude = mapHelper.getLocation().longitude
        if (viewDataBinding.toolbarNewPark.title.isNullOrEmpty())
            history.parkAddress = resources.getString(R.string.label_error_find_address)
        else
            history.parkAddress = viewDataBinding.toolbarNewPark.title.toString()
        history.parkClock = persianDate.hour.toString() + ":" + persianDate.minute
        history.parkDate = persianDateFormat.format(persianDate)
        mViewModel.insertHistory(history)
    }

    override fun onStartInitializeMap() {
        sharedViewModel.setProgressbar(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        showExtraViews()
        sharedViewModel.setProgressbar(false)
        mapHelper.apply {
            moveCamera(Constants.TEHRAN_LATLNG, Constants.LOCATION_ZOOM, false)
            getUiSettings().isMyLocationButtonEnabled = false
            getUiSettings().isCompassEnabled = false
            getUiSettings().isRotateGesturesEnabled = false
            if (isNightMode) {
                setMapStyle(R.raw.style)
            }
            setTrafficEnabled(
                viewModel.getPreference(
                    Constants.KEY_TRAFFIC,
                    Constants.TRAFFIC_DEFAULT_VALUE
                )
            )
        }
        googleMap.setOnCameraIdleListener { mViewModel.getLocationGeoAddress(mapHelper.getLocation()) }
        googleMap.setOnCameraMoveStartedListener {
            viewModel.cancelReverseGeoCoderRequest()
            viewDataBinding.progressbarParkGeo.visibility = View.GONE
        }
        mapHelper.getMapFragment().setOnTouchListener(this)
        getLastKnowLocation()
    }

    private fun getLastKnowLocation() {
        if (locationHelper.isRequiredPermissionRequest()) {
            if (locationHelper.isLocationPermissionsGranted()) {
                locationHelper.checkLocationSettings().addOnSuccessListener {
                    initLastKnowLocation()
                }.addOnFailureListener { exception ->
                    mViewModel.setLocationButtonState(false)
                    if (exception is ResolvableApiException) {
                        try {
                            exception.startResolutionForResult(
                                requireActivity(),
                                Constants.LOCATION_DIALOG_REQUEST_CODE
                            )
                        } catch (sendEx: IntentSender.SendIntentException) {
                            Log.e(
                                TAG,
                                "Error Start Activity For Result",
                                sendEx
                            )
                        }

                    }
                }
            } else {
                locationHelper.requestLocationPermissions(
                    baseActivity,
                    Constants.LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun initLastKnowLocation() {
        locationHelper.getLastLocation().addOnSuccessListener { location ->
            if (location != null) {
                if (mapHelper.isMapReady()) {
                    mapHelper.setMyLocationEnabled(true)
                    mapHelper.moveCamera(location, Constants.LOCATION_ZOOM, true)
                    mViewModel.setLocationButtonState(true)
                }
            } else {
                Log.e(TAG, "Last Location Result is Null")
                locationHelper.requestLocationUpdates(LocationUpdateCallback())
            }
        }
    }

    override fun onTouch(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                viewDataBinding.imgNewParkMarker.animate()
                    .translationY(Constants.ACTION_DOWN_Y_TRANSLATION).start()
            }
            MotionEvent.ACTION_UP -> {
                viewDataBinding.imgNewParkMarker.animate()
                    .translationY(Constants.ACTION_UP_Y_TRANSLATION).start()
            }
        }
    }

    inner class LocationUpdateCallback : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            mapHelper.moveCamera(p0?.locations?.get(0)!!, Constants.LOCATION_ZOOM, true)
            mapHelper.setMyLocationEnabled(true)
            mViewModel.setLocationButtonState(true)
            locationHelper.removeLocationUpdates(this)
        }
    }
}