package ir.esmaeili.stopcar.di.splah

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.splash.SplashFragment

@Module
abstract class SplashBindsModule {
    @Binds
    @SplashScope
    abstract fun bindsSplashSavedStateOwner(fragment: SplashFragment): SavedStateRegistryOwner
}