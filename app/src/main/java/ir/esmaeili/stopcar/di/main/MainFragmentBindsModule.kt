package ir.esmaeili.stopcar.di.main

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.main.MainFragment

@Module
abstract class MainFragmentBindsModule {
    @Binds
    @MainFragmentScope
    abstract fun bindsMainFragmentSavedStateOwner(fragment: MainFragment): SavedStateRegistryOwner
}