package ir.esmaeili.stopcar.models

import android.view.Display
import com.google.gson.annotations.SerializedName

data class AddressGeoCoder(
    @SerializedName("status") val status: String,
    @SerializedName("formatted_address") val formattedAddress: String,
    @SerializedName("route_name") val routeName: String,
    @SerializedName("route_type") val routeType: String,
    @SerializedName("neighbourhood") val neighbourhood: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("place") val place: String,
    @SerializedName("municipality_zone") val municipalityZone: String,
    @SerializedName("in_traffic_zone") val inTrafficZone: Boolean,
    @SerializedName("in_odd_even_zone") val inOddEvenZone: Boolean,
    @SerializedName("addresses") val addresses: List<Address>
)