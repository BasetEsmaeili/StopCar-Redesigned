package ir.esmaeili.stopcar.models.dao

import androidx.room.*
import io.reactivex.Flowable
import ir.esmaeili.stopcar.models.History
import ir.esmaeili.stopcar.models.HistoryJoinCar

@Dao
interface HistoryDao {
    @Insert
    fun insert(history: History)

    @Query("SELECT * FROM history LEFT JOIN cars on history.car_id=cars.car_id ORDER BY history_id DESC")
    fun getHistory(): Flowable<List<HistoryJoinCar>>

    @Query("DELETE FROM history WHERE car_id=:carID")
    fun deleteCarHistory(carID: Long)

    @Query("DELETE FROM history")
    fun deleteAll()
}