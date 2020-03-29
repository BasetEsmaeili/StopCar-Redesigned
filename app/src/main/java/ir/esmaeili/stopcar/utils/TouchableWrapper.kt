package ir.esmaeili.stopcar.utils

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

class TouchableWrapper(
    context: Context
) : FrameLayout(context) {
    companion object {
        const val TAG: String = "TouchableWrapper"
    }

    private lateinit var onTouchListener: ir.esmaeili.stopcar.utils.OnTouchListener
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        try {
            onTouchListener.onTouch(ev!!)
        } catch (e: UninitializedPropertyAccessException) {
            Log.e(TAG, "", e)
        }
        return super.dispatchTouchEvent(ev)
    }

    fun setTouchListener(onTouchListener: ir.esmaeili.stopcar.utils.OnTouchListener) {
        this.onTouchListener = onTouchListener
    }

}