package ir.esmaeili.stopcar.di.cars

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.cars.ManageCarsFragment

@Module
abstract class ManageCarsBindsModule {
    @Binds
    @ManageCarsScope
    abstract fun bindsManageCarsFragmentViewModelProviderFactory(fragment: ManageCarsFragment): SavedStateRegistryOwner
}