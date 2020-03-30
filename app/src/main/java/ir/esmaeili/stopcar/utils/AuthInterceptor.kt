package ir.esmaeili.stopcar.utils

import ir.esmaeili.stopcar.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().header("Api-Key", BuildConfig.NeshanWebSerivceApiKey)
                .build()
        return chain.proceed(request)
    }
}