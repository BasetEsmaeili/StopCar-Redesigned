package ir.esmaeili.stopcar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker
import ir.esmaeili.stopcar.R

class CustomInfoWindowAdapter(
    private val
    context: Context,
    private val address: String
) : InfoWindowAdapter {
    override fun getInfoWindow(marker: Marker): View {
        val view = LayoutInflater.from(context).inflate(R.layout.view_info_window, null, false)
        view.findViewById<TextView>(R.id.tv_info_address).text = address
        return view
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }

}