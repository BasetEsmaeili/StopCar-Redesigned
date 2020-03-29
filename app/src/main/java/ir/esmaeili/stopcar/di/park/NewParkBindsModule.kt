package ir.esmaeili.stopcar.di.park

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.park.NewParkFragment

@Module
abstract class NewParkBindsModule {
    @Binds
    @NewParkScope
    abstract fun bindsNewParkSavedStateOwner(fragment: NewParkFragment): SavedStateRegistryOwner
}