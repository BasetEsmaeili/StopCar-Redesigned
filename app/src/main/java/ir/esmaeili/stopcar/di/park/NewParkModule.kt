package ir.esmaeili.stopcar.di.park

import android.content.Context
import dagger.Module
import dagger.Provides
import ir.esmaeili.stopcar.utils.LocationHelper

@Module
class NewParkModule {
    @Provides
    @NewParkScope
    fun providerLocationHelper(context: Context): LocationHelper {
        return LocationHelper(context)
    }
}