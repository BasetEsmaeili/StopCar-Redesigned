package ir.esmaeili.stopcar.di.dialog

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.park.SelectCarDialogFragment

@Module
abstract class SelectCarDialogBindsModule {
    @Binds
    @SelectCarDialogScope
    abstract fun providerSelectCarDialogStateOwner(selectCarDialogFragment: SelectCarDialogFragment): SavedStateRegistryOwner
}