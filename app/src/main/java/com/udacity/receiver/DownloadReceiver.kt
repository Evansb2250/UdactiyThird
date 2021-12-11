package com.udacity.receiver

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.udacity.activity.MainActivity
import com.udacity.environmentvariables.EnvironmentVariables.DOWNLOAD_ID
import com.udacity.util.sendNotification


object DownloadReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        context?.let { context ->
            if (id == DOWNLOAD_ID ) {
                sendDownloadStatus(true, context)
            } else
                sendDownloadStatus(false, context)
        }
    }
}


private fun sendDownloadStatus(downloadSuccessful: Boolean, context: Context) {
    val message = if (downloadSuccessful) "Download success" else "Download failed"
    val notificationManager = context.getSystemService(NotificationManager::class.java)
    notificationManager.sendNotification(message, context)
}

