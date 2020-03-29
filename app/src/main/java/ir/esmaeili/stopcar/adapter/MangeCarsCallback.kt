package ir.esmaeili.stopcar.adapter

import ir.esmaeili.stopcar.models.Car

interface MangeCarsCallback {
    fun onCarRemoved(car: Car)
}