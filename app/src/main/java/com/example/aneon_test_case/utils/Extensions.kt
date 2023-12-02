package com.example.aneon_test_case.utils

import android.graphics.Color
import androidx.annotation.ColorInt
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun TextInputLayout.setErrorAndColor(error: CharSequence?, @ColorInt color: Int) {
    if (error != null) {
        isErrorEnabled = true
        this.error = error
        boxStrokeColor = color
    } else {
        isErrorEnabled = false
        boxStrokeColor = Color.BLACK
    }
}

fun Long.convertTimestampToDateString(): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val date = Date(this * 1000)
    return sdf.format(date)
}







