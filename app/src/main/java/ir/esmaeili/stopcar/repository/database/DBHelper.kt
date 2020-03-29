package ir.esmaeili.stopcar.repository.database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import ir.esmaeili.stopcar.models.Car
import ir.esmaeili.stopcar.models.History
import ir.esmaeili.stopcar.models.HistoryJoinCar

interface DBHelper {
    fun insertCar(car: Car): Single<Boolean>
    fun getCars(): Flowable<List<Car>>
    fun insertHistory(history: History): Single<Boolean>
    fun getHistoryList(): Flowable<List<HistoryJoinCar>>
    fun deleteCarWithHistory(carID: Long): Single<Boolean>
    fun getCarCount(): Flowable<Long>
    fun deleteAll(): Completable
}