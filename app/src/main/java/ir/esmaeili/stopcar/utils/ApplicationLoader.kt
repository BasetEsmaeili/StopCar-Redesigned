package ir.esmaeili.stopcar.utils

import android.content.Context
import androidx.multidex.MultiDex
import com.google.android.gms.maps.MapsInitializer
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ir.esmaeili.stopcar.di.app.AppComponent
import ir.esmaeili.stopcar.di.app.DaggerAppComponent

class ApplicationLoader : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent: AppComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}