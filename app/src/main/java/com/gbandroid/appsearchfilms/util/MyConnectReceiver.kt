package com.gbandroid.appsearchfilms.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyConnectReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val message = "Сетевое соединение изменилось!!!\n${intent.action.toString()}"
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}