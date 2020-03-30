package ir.esmaeili.stopcar.repository.network

import io.reactivex.Single
import ir.esmaeili.stopcar.models.AddressGeoCoder
import retrofit2.Retrofit

class ApiHelperImpl(retrofit: Retrofit) :
    ApiHelper {
    private val apiHelper: ApiHelper = retrofit.create(ApiHelper::class.java)
    override fun geoCoderAddress(
        latitude: String,
        longitude: String
    ): Single<AddressGeoCoder> {
        return apiHelper.geoCoderAddress(latitude, longitude)
    }


}