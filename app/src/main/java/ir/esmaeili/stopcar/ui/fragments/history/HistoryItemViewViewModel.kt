package ir.esmaeili.stopcar.ui.fragments.history

import androidx.databinding.ObservableField
import ir.esmaeili.stopcar.models.HistoryJoinCar

class HistoryItemViewViewModel(
    private val callback: HistoryCallback
) {
    private val carColor: ObservableField<String> = ObservableField()
    private val parkDate: ObservableField<String> = ObservableField()
    private val parkClock: ObservableField<String> = ObservableField()
    private val parkAddress: ObservableField<String> = ObservableField()
    private val isToday: ObservableField<Boolean> = ObservableField()
    private val carName: ObservableField<String> = ObservableField()
    private val carModel: ObservableField<String> = ObservableField()
    private lateinit var history: HistoryJoinCar
    fun setCarModel(model: String) {
        this.carModel.set(model)
    }

    fun getCarModel(): ObservableField<String> {
        return this.carModel
    }

    fun setCarName(name: String) {
        this.carName.set(name)
    }

    fun getCarName(): ObservableField<String> {
        return this.carName
    }

    fun setCarColor(value: String) {
        this.carColor.set(value)
    }

    fun setIsToday(value: Boolean) {
        this.isToday.set(value)
    }

    fun getIsToday(): ObservableField<Boolean> {
        return isToday
    }

    fun setCarParkDate(value: String) {
        this.parkDate.set(value)
    }

    fun setCarParkClock(value: String) {
        this.parkClock.set(value)
    }

    fun setCarParkAddress(value: String) {
        this.parkAddress.set(value)
    }

    fun getCarColor(): ObservableField<String> {
        return carColor
    }

    fun getCarParkDate(): ObservableField<String> {
        return parkDate
    }

    fun getCarParkClock(): ObservableField<String> {
        return parkClock
    }

    fun getCarParkAddress(): ObservableField<String> {
        return parkAddress
    }

    fun setHistory(history: HistoryJoinCar) {
        this.history = history
    }

    fun onHistoryItemClick() {
        callback.onHistoryItemClick(history)
    }

}