package ir.esmaeili.stopcar.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

class NewCarEditTextChangeListener(private val length: Int, private val editText: TextInputLayout) :
    TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s?.length == length) {
            editText.requestFocus()
        }
    }
}