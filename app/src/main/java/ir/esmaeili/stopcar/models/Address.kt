package ir.esmaeili.stopcar.models

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("formatted") val formatted: String,
    @SerializedName("components") val components: List<Components>
)