package com.example.booksapp.domain.tools

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.booksapp.R
import com.google.android.material.snackbar.Snackbar

class SnackBars {
    fun snackBar(container : View, layoutInflater: LayoutInflater){
        val snackbar = Snackbar.make(
            container,
            "",
            Snackbar.LENGTH_LONG
        )
        val costume =
            layoutInflater.inflate(R.layout.error_alert_info, null)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val textOfError = costume.findViewById<TextView>(R.id.alertTextView)
        val bg = costume.findViewById<LinearLayout>(R.id.alertBg)
        val imageOfError = costume.findViewById<ImageView>(R.id.alertErrorImage)
        val snackbarlayout: Snackbar.SnackbarLayout =
            snackbar.view as Snackbar.SnackbarLayout
        snackbarlayout.setPadding(0, 0, 0, 0)
        snackbarlayout.addView(costume)
        snackbar.show()
    }
}