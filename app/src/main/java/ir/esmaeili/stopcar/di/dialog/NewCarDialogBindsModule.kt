package ir.esmaeili.stopcar.di.dialog

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.cars.NewCarDialogFragment

@Module
abstract class NewCarDialogBindsModule {
    @Binds
    @NewCarDialogScope
    abstract fun newCarDialogViewModelSavedStateOwner(fragment: NewCarDialogFragment): SavedStateRegistryOwner
}