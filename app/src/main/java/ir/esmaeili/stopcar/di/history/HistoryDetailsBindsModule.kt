package ir.esmaeili.stopcar.di.history

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.detail.HistoryDetailsFragment

@Module
abstract class HistoryDetailsBindsModule {
    @Binds
    @HistoryDetailScope
    abstract fun bindsHistoryDetailsViewModelOwner(fragment: HistoryDetailsFragment): SavedStateRegistryOwner
}