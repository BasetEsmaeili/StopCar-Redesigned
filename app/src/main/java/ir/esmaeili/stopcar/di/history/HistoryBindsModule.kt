package ir.esmaeili.stopcar.di.history

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.history.HistoryFragment

@Module
abstract class HistoryBindsModule {
    @Binds
    @HistoryScope
    abstract fun bindHistorySavedStateOwner(owner: HistoryFragment): SavedStateRegistryOwner
}