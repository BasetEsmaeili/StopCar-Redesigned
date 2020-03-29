package ir.esmaeili.stopcar.di.intro

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import ir.esmaeili.stopcar.ui.fragments.intro.IntroFragment

@Module
abstract class IntroBindsModule {
    @Binds
    @IntroScope
    abstract fun bindsIntroViewModelProviderFactory(intro: IntroFragment): SavedStateRegistryOwner
}