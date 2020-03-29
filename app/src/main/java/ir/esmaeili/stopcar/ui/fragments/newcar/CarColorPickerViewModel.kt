package ir.esmaeili.stopcar.ui.fragments.newcar

import androidx.databinding.ObservableField
import ir.esmaeili.stopcar.models.CarColor

class CarColorPickerViewModel(private val callback: CarColorCallback) {
    private var color: ObservableField<String> = ObservableField()
    fun setColor(value: String) {
        color.set(value)
    }

    fun getColor(): ObservableField<String> {
        return color
    }

    fun onItemClick() {
        callback.onColorPicked(CarColor(color.get()!!))
    }
}