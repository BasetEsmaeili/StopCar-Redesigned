package ir.esmaeili.stopcar.di.cars

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.newcar.NewCarFragment

@Module
abstract class NewCarBindsModule {
    @Binds
    @NewCarScope
    abstract fun bindsNewCarViewModelSavedStateOwner(fragmentFragment: NewCarFragment): SavedStateRegistryOwner
}