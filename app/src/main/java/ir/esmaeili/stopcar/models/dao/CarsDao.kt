package ir.esmaeili.stopcar.models.dao

import androidx.room.*
import io.reactivex.Flowable
import ir.esmaeili.stopcar.models.Car

@Dao
interface CarsDao {
    @Insert
    fun insert(car: Car)

    @Query("SELECT * FROM cars")
    fun getCars(): Flowable<List<Car>>

    @Query("SELECT COUNT(car_id) FROM cars")
    fun getCarCount(): Flowable<Long>

    @Query("DELETE FROM cars WHERE car_id=:carID")
    fun deleteCar(carID: Long)

    @Query("DELETE FROM cars")
    fun deleteAll()

}