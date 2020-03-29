package ir.esmaeili.stopcar.ui.fragments.cars

import androidx.databinding.ObservableField

class ManageCarsItemViewViewModel {
    private val carName: ObservableField<String> = ObservableField()
    private val carColor: ObservableField<String> = ObservableField()

    fun setCarName(carName: String) {
        this.carName.set(carName)
    }

    fun setCarColor(carColor: String) {
        this.carColor.set(carColor)
    }


    fun getCarColor(): ObservableField<String> {
        return this.carColor
    }

    fun getCarName(): ObservableField<String> {
        return this.carName
    }
}