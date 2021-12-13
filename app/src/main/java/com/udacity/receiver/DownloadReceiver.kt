package com.udacity.receiver

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.udacity.environmentvariables.EnvironmentVariables.DOWNLOAD_ID
import com.udacity.download_util.sendNotification
import com.udacity.environmentvariables.DownloadStatus
import com.udacity.environmentvariables.download_failed
import com.udacity.environmentvariables.download_success


object DownloadReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val downloadSuccessful:Boolean = intent?.getBooleanExtra(DownloadStatus,false)?:false
        context?.let { context ->
            if (downloadSuccessful) {
                sendDownloadStatus(true, context, intent)
            } else
                sendDownloadStatus(false, context, intent)
        }
    }
}




private fun sendDownloadStatus(downloadSuccessful: Boolean, context: Context, intent: Intent?) {
    val title = intent?.getExtras()?.get("title")?.toString()?:"Unkown File Name"
    val message = if (downloadSuccessful) download_success else download_failed
    val notificationManager = context.getSystemService(NotificationManager::class.java)
    notificationManager.sendNotification(message, title, context)
}

