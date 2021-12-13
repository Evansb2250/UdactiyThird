package com.udacity.download_util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.udacity.R
import com.udacity.activity.DetailActivity
import com.udacity.activity.MainActivity.Companion.CHANNEL
import com.udacity.activity.MainActivity.Companion.NOTIFICATION_ID
import com.udacity.environmentvariables.DownloadStatus
import com.udacity.environmentvariables.download_success


fun NotificationManager.sendNotification(
    messageBody: String,
    fileName: String,
    applicationContext: Context,
){

    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
        contentIntent.putExtra(DownloadStatus, if(messageBody.equals(download_success)) true else false )
        contentIntent.putExtra("FileName", fileName)



    val pendingIntent =  PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    // builder
    val builder = NotificationCompat.Builder(applicationContext, CHANNEL)
        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle("Download Test")
        .setContentText(messageBody)
        .setPriority(NotificationManager.IMPORTANCE_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)


    // notification manager
    notify(NOTIFICATION_ID, builder.build())


}