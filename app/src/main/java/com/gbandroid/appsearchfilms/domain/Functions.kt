package com.gbandroid.appsearchfilms.util

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar(text: String, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, text, length).show()
}