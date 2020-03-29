package ir.esmaeili.stopcar.di.app

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.esmaeili.stopcar.di.main.MainActivityModule
import ir.esmaeili.stopcar.di.main.MainActivityScope
import ir.esmaeili.stopcar.ui.activity.MainActivity

@Module
abstract class ActivityBuilder {
    @MainActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class, FragmentsProvider::class])
    abstract fun bindMainActivity(): MainActivity
}