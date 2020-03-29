package ir.esmaeili.stopcar.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.esmaeili.stopcar.models.Car
import ir.esmaeili.stopcar.models.History
import ir.esmaeili.stopcar.models.dao.CarsDao
import ir.esmaeili.stopcar.models.dao.HistoryDao

@Database(entities = [Car::class, History::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getCarDao(): CarsDao
    abstract fun getHistoryDao(): HistoryDao
}