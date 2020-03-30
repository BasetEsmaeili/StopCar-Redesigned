package ir.esmaeili.stopcar.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import ir.esmaeili.stopcar.models.*
import ir.esmaeili.stopcar.repository.database.DBHelperImpl
import ir.esmaeili.stopcar.repository.local.StaticDataHelperImpl
import ir.esmaeili.stopcar.repository.network.ApiHelperImpl
import ir.esmaeili.stopcar.repository.preferences.PreferencesHelperImpl
import javax.inject.Inject

class RepositoryManagerImpl @Inject constructor(
    private val staticDataHelperImpl: StaticDataHelperImpl,
    private val appDataBase: DBHelperImpl,
    private val preferencesHelperImpl: PreferencesHelperImpl,
    private val apiHelperImpl: ApiHelperImpl
) :
    RepositoryManager {
    override fun <T : Any> getSharedPreference(key: String, defaultValue: T): T {
        return preferencesHelperImpl.getSharedPreference(key, defaultValue)
    }

    override fun isIntroPageFinished(): Boolean {
        return preferencesHelperImpl.isIntroPageFinished()
    }

    override fun geoCoderAddress(
        latitude: String,
        longitude: String
    ): Single<AddressGeoCoder> {
        return apiHelperImpl.geoCoderAddress(latitude, longitude)
    }

    override fun editSharedPreference(key: String, value: Any) {
        return preferencesHelperImpl.editSharedPreference(key, value)
    }

    override fun getHistoryList(): Flowable<List<HistoryJoinCar>> {
        return appDataBase.getHistoryList()
    }

    override fun deleteCarWithHistory(carID: Long): Single<Boolean> {
        return appDataBase.deleteCarWithHistory(carID)
    }

    override fun getCarCount(): Flowable<Long> {
        return appDataBase.getCarCount()
    }

    override fun deleteAll(): Completable {
        return appDataBase.deleteAll()
    }

    override fun getCars(): Flowable<List<Car>> {
        return appDataBase.getCars()

    }

    override fun insertHistory(history: History): Single<Boolean> {
        return appDataBase.insertHistory(history)
    }

    override fun getCarColors(): ArrayList<CarColor> {
        return staticDataHelperImpl.getCarColors()
    }

    override fun getNeshanApiKey(): String {
        return staticDataHelperImpl.getNeshanApiKey()
    }

    override fun insertCar(car: Car): Single<Boolean> {
        return appDataBase.insertCar(car)
    }

    override fun getIntroItems(): List<Slide> {
        return staticDataHelperImpl.getIntroItems()
    }
}