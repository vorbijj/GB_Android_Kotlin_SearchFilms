package com.gbandroid.appsearchfilms.util

import android.app.IntentService
import android.content.Intent
import android.util.Log
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

private const val TAG = "MainServiceTAG"
private const val TAG_TXT = "MainServiceTXT"
private const val FILENAME = "ServiceTXT.txt"
const val MAIN_SERVICE_STRING_EXTRA = "MainServiceExtra"

class MainMonitorIntentService() : IntentService("MainMonitorIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        val message = intent?.getStringExtra(MAIN_SERVICE_STRING_EXTRA)
        if (message != null) {
            writeLog(message)
            writeText(message)
        }
    }

    private fun writeText(message: String) {
        try {
            val fileToWrite: FileOutputStream = openFileOutput(FILENAME, MODE_APPEND)
            fileToWrite.write(message.toByteArray())
            fileToWrite.close()
            Log.d(TAG_TXT, "Файл записан")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun writeLog(message: String) {
        Log.d(TAG, message)
    }
}