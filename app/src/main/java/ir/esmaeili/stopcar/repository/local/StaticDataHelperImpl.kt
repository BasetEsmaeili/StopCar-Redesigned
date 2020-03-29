package ir.esmaeili.stopcar.repository.local

import android.content.Context
import ir.esmaeili.stopcar.BuildConfig
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.models.CarColor
import ir.esmaeili.stopcar.models.Slide
import javax.inject.Inject


class StaticDataHelperImpl @Inject constructor(val context: Context) :
    StaticDataHelper {
    override fun getCarColors(): ArrayList<CarColor> {
        val colors: ArrayList<CarColor> = ArrayList()
        colors.add(CarColor("#ffffff"))
        colors.add(CarColor("#bdbdbd"))
        colors.add(CarColor("#607d8b"))
        colors.add(CarColor("#212121"))
        colors.add(CarColor("#4F4E4C"))
        colors.add(CarColor("#ff5722"))
        colors.add(CarColor("#795548"))
        colors.add(CarColor("#BB2516"))
        colors.add(CarColor("#5e35b1"))
        colors.add(CarColor("#2196f3"))
        colors.add(CarColor("#8bc34a"))
        colors.add(CarColor("#388e3c"))
        colors.add(CarColor("#ffc107"))
        colors.add(CarColor("#ffee58"))
        colors.add(CarColor("#040836"))
        colors.add(CarColor("#4E0A0B"))
        colors.add(CarColor("#03DACB"))
        colors.add(CarColor("#ec407a"))
        colors.add(CarColor("#009688"))
        colors.add(CarColor("#9c27b0"))
        return colors
    }

    override fun getNeshanApiKey(): String {
        return BuildConfig.NeshanWebSerivceApiKey
    }

    override fun getIntroItems(): List<Slide> {
        val introList: ArrayList<Slide> = ArrayList()
        introList.add(
            Slide(
                "car_park.json",
                context.resources.getString(R.string.label_intro_title_1),
                context.resources.getString(R.string.label_intro_description_1)
            )
        )
        introList.add(
            Slide(
                "car_location.json",
                context.resources.getString(R.string.label_intro_title_2),
                context.resources.getString(R.string.label_intro_description_2)
            )
        )
        introList.add(
            Slide(
                "car_history_saved.json",
                context.resources.getString(R.string.label_intro_title_3),
                context.resources.getString(R.string.label_intro_description_3)
            )
        )
        return introList
    }
}