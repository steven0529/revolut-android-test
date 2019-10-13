package com.revolut.rates.core.extensions

import android.widget.EditText

fun EditText.placeCursorToEnd() {
    this.setSelection(this.text.length)
}
