package ir.esmaeili.stopcar.repository.local

import ir.esmaeili.stopcar.models.CarColor
import ir.esmaeili.stopcar.models.Slide

interface StaticDataHelper {
    fun getIntroItems(): List<Slide>
    fun getCarColors(): ArrayList<CarColor>
    fun getNeshanApiKey(): String
}