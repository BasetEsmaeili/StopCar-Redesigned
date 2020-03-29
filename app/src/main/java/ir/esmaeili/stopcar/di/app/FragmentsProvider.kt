package ir.esmaeili.stopcar.di.app

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.esmaeili.stopcar.di.cars.*
import ir.esmaeili.stopcar.di.dialog.*
import ir.esmaeili.stopcar.di.history.*
import ir.esmaeili.stopcar.di.intro.IntroBindsModule
import ir.esmaeili.stopcar.di.intro.IntroScope
import ir.esmaeili.stopcar.di.main.MainFragmentScope
import ir.esmaeili.stopcar.di.main.MainFragmentBindsModule
import ir.esmaeili.stopcar.di.menu.MenuBindsModule
import ir.esmaeili.stopcar.di.menu.MenuScope
import ir.esmaeili.stopcar.di.park.NewParkBindsModule
import ir.esmaeili.stopcar.di.park.NewParkModule
import ir.esmaeili.stopcar.di.park.NewParkScope
import ir.esmaeili.stopcar.di.splah.SplashBindsModule
import ir.esmaeili.stopcar.di.splah.SplashScope
import ir.esmaeili.stopcar.ui.fragments.cars.ManageCarsFragment
import ir.esmaeili.stopcar.ui.fragments.cars.NewCarDialogFragment
import ir.esmaeili.stopcar.ui.fragments.detail.HistoryDetailsFragment
import ir.esmaeili.stopcar.ui.fragments.history.HistoryFragment
import ir.esmaeili.stopcar.ui.fragments.intro.IntroFragment
import ir.esmaeili.stopcar.ui.fragments.main.MainFragment
import ir.esmaeili.stopcar.ui.fragments.menu.MenuFragment
import ir.esmaeili.stopcar.ui.fragments.newcar.ColorPickDialogFragment
import ir.esmaeili.stopcar.ui.fragments.newcar.NewCarFragment
import ir.esmaeili.stopcar.ui.fragments.park.NewParkFragment
import ir.esmaeili.stopcar.ui.fragments.park.SelectCarDialogFragment
import ir.esmaeili.stopcar.ui.fragments.splash.SplashFragment

@Module
abstract class FragmentsProvider {
    @SplashScope
    @ContributesAndroidInjector(modules = [SplashBindsModule::class])
    abstract fun bindSplash(): SplashFragment

    @IntroScope
    @ContributesAndroidInjector(modules = [IntroBindsModule::class])
    abstract fun bindIntro(): IntroFragment

    @NewCarScope
    @ContributesAndroidInjector(modules = [NewCarBindsModule::class])
    abstract fun bindNewCar(): NewCarFragment

    @MainFragmentScope
    @ContributesAndroidInjector(modules = [MainFragmentBindsModule::class])
    abstract fun bindMain(): MainFragment

    @HistoryScope
    @ContributesAndroidInjector(modules = [HistoryBindsModule::class])
    abstract fun bindHistory(): HistoryFragment

    @NewParkScope
    @ContributesAndroidInjector(modules = [NewParkModule::class, NewParkBindsModule::class])
    abstract fun bindNewPark(): NewParkFragment

    @ManageCarsScope
    @ContributesAndroidInjector(modules = [ManageCarsBindsModule::class])
    abstract fun bindManageCars(): ManageCarsFragment

    @MenuScope
    @ContributesAndroidInjector(modules = [MenuBindsModule::class])
    abstract fun bindMenu(): MenuFragment

    @NewCarDialogScope
    @ContributesAndroidInjector(modules = [NewCarDialogBindsModule::class])
    abstract fun bindNewCarDialog(): NewCarDialogFragment

    @SelectCarDialogScope
    @ContributesAndroidInjector(modules = [SelectCarDialogBindsModule::class])
    abstract fun bindSelectCarDialog(): SelectCarDialogFragment

    @ColorPickDialogScope
    @ContributesAndroidInjector(modules = [ColorPickDialogBindsModule::class])
    abstract fun bindColorPickDialog(): ColorPickDialogFragment

    @HistoryDetailScope
    @ContributesAndroidInjector(modules = [HistoryDetailsBindsModule::class])
    abstract fun bindHistoryDetails(): HistoryDetailsFragment
}