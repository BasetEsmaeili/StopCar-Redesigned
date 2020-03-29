package ir.esmaeili.stopcar.di.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import ir.esmaeili.stopcar.utils.ApplicationLoader

@AppScope
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilder::class, AppBindsModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {
    fun inject(applicationLoader: ApplicationLoader)
    override fun inject(instance: DaggerApplication?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}