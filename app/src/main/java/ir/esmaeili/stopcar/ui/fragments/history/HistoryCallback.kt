package ir.esmaeili.stopcar.ui.fragments.history

import ir.esmaeili.stopcar.models.HistoryJoinCar

interface HistoryCallback {
    fun onHistoryItemClick(history: HistoryJoinCar)
}