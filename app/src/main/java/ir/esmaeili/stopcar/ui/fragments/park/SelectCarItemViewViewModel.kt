package ir.esmaeili.stopcar.ui.fragments.park

import androidx.databinding.ObservableField
import ir.esmaeili.stopcar.models.Car

class SelectCarItemViewViewModel(private val callback: SelectCarCallback) {
    private lateinit var car: Car
    private var carColor: ObservableField<String> = ObservableField()
    private var carName: ObservableField<String> = ObservableField()

    fun getCarColor(): ObservableField<String> {
        return carColor
    }

    fun getCarName(): ObservableField<String> {
        return carName
    }

    fun setCar(car: Car) {
        this.car = car
    }

    fun setCarColor(value: String) {
        this.carColor.set(value)
    }

    fun setCarName(value: String) {
        this.carName.set(value)
    }

    fun onCarSelected() {
        callback.onCarSelected(car)
    }
}