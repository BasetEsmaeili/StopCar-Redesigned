package ir.esmaeili.stopcar.repository.network

import io.reactivex.Single
import ir.esmaeili.stopcar.models.AddressGeoCoder
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ApiHelper {

    @GET("reverse?")
    fun geoCoderAddress(
        @Query("lat") latitude: String,
        @Query("lng") longitude: String,
        @Header("Api-Key") api_key: String
    ): Single<AddressGeoCoder>


}