package ir.esmaeili.stopcar.di.app

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class AppBindsModule {
    @Binds
    @AppScope
    abstract fun providerContext(application: Application): Context
}