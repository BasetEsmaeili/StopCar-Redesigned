package ir.esmaeili.stopcar.ui.fragments.park

import ir.esmaeili.stopcar.models.Car

interface SelectCarCallback {
    fun onCarSelected(car: Car)
}