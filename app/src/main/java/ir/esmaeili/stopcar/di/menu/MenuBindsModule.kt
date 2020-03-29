package ir.esmaeili.stopcar.di.menu

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.menu.MenuFragment

@Module
abstract class MenuBindsModule {
    @Binds
    @MenuScope
    abstract fun bindMenuFragmentSavedStateOwner(fragment: MenuFragment): SavedStateRegistryOwner
}