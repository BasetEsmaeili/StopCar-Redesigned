package ir.esmaeili.stopcar.repository.database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import ir.esmaeili.stopcar.models.Car
import ir.esmaeili.stopcar.models.History
import ir.esmaeili.stopcar.models.HistoryJoinCar
import javax.inject.Inject

class DBHelperImpl @Inject constructor(private val appDataBase: AppDataBase) :
    DBHelper {
    override fun insertCar(car: Car): Single<Boolean> {
        return Single.fromCallable {
            appDataBase.getCarDao().insert(car)
            true
        }
    }

    override fun getCars(): Flowable<List<Car>> {
        return appDataBase.getCarDao().getCars()
    }

    override fun insertHistory(history: History): Single<Boolean> {
        return Single.fromCallable {
            appDataBase.getHistoryDao().insert(history)
            true
        }
    }

    override fun getHistoryList(): Flowable<List<HistoryJoinCar>> {
        return appDataBase.getHistoryDao().getHistory()
    }

    override fun deleteCarWithHistory(carID: Long): Single<Boolean> {
        return Single.fromCallable {
            appDataBase.getHistoryDao().deleteCarHistory(carID)
            appDataBase.getCarDao().deleteCar(carID)
            true
        }
    }

    override fun getCarCount(): Flowable<Long> {
        return appDataBase.getCarDao().getCarCount()
    }

    override fun deleteAll(): Completable {
        return Completable.fromCallable {
            appDataBase.getHistoryDao().deleteAll()
            appDataBase.getCarDao().deleteAll()
        }
    }

}