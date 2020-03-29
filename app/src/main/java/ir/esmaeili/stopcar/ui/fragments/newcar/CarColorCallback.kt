package ir.esmaeili.stopcar.ui.fragments.newcar

import ir.esmaeili.stopcar.models.CarColor

interface CarColorCallback {
    fun onColorPicked(carColor: CarColor)
}