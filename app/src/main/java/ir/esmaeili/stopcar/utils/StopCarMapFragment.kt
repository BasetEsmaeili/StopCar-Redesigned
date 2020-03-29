package ir.esmaeili.stopcar.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment

class StopCarMapFragment : SupportMapFragment() {
    private lateinit var mapView: View
    private lateinit var touchableWrapper: TouchableWrapper
    private var isViewInitialized = false

    companion object {
        fun newInstance(): StopCarMapFragment {
            return StopCarMapFragment()
        }
    }

    override fun onCreateView(p0: LayoutInflater, p1: ViewGroup?, p2: Bundle?): View? {
        if (!isViewInitialized) {
            mapView = super.onCreateView(p0, p1, p2)!!
            touchableWrapper = TouchableWrapper(requireActivity())
            touchableWrapper.addView(mapView)
            isViewInitialized = true
        }
        return touchableWrapper
    }

    override fun getView(): View? {
        return mapView
    }

    fun setOnTouchListener(listener: OnTouchListener) {
        touchableWrapper.setTouchListener(listener)
    }
}