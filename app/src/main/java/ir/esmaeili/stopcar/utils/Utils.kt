package ir.esmaeili.stopcar.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.textfield.TextInputEditText
import java.nio.charset.Charset


class Utils(private val context: Context) {
    fun hideKeyboard(activity: Activity) {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager!!.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken,
            HIDE_NOT_ALWAYS
        )
    }

    fun inputIsNotEmpty(input: TextInputEditText): Boolean {
        return input.text?.isNotEmpty()!!
    }

    fun setTypeface(fontName: String): Typeface {
        return Typeface.createFromAsset(context.assets, "$fontName.ttf")
    }

    fun getColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(context, color)
    }


    fun getDrawable(@DrawableRes drawable: Int): Drawable {
        return ContextCompat.getDrawable(context, drawable)!!
    }


    fun getString(@StringRes int: Int): String {
        return context.resources.getString(int)
    }

    fun readAssetsText(name: String): String {
        val builder = StringBuilder()
        val stream = context.assets.open(name)
        stream.bufferedReader(Charset.defaultCharset())
            .useLines { it.forEach { s -> builder.append(s + "\n") } }
        return builder.toString()
    }

    fun convertDpToPx(@Dimension(unit = Dimension.DP) value: Float): Float {
        return value * context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
    }


    fun getBitmapFromDrawable(
        @DrawableRes resource: Int, @Dimension(unit = Dimension.DP) width: Float, @Dimension(
            unit = Dimension.DP
        ) height: Float
    ): Bitmap {
        return AppCompatResources.getDrawable(context, resource)?.toBitmap(
            convertDpToPx(width).toInt(),
            convertDpToPx(height).toInt(),
            Bitmap.Config.ARGB_8888
        )!!
    }
}