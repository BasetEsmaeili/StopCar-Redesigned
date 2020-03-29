package ir.esmaeili.stopcar.models

import android.content.DialogInterface
import androidx.annotation.DrawableRes

sealed class AlertView {
    data class CustomToast(
        val eventType: EventType,
        val title: String,
        val description: String,
        @DrawableRes val icon: Int
    ) : AlertView()

    data class AlertDialog(
        val title: String,
        val message: String,
        val positiveText: String,
        val isCancelable: Boolean,
        val clickListener: DialogInterface.OnClickListener
    ) : AlertView()
}