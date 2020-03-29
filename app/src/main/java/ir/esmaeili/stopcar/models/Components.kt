package ir.esmaeili.stopcar.models

import com.google.gson.annotations.SerializedName

data class Components(
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String
)