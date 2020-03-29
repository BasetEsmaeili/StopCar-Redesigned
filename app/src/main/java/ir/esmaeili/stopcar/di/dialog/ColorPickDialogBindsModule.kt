package ir.esmaeili.stopcar.di.dialog

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.newcar.ColorPickDialogFragment

@Module
abstract class ColorPickDialogBindsModule {
    @Binds
    @ColorPickDialogScope
    abstract fun providerBottomSheetStateOwner(dialog: ColorPickDialogFragment): SavedStateRegistryOwner
}