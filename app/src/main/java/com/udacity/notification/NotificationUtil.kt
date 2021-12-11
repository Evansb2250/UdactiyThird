package com.udacity.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.udacity.activity.MainActivity
import com.udacity.R
import com.udacity.activity.MainActivity.Companion.CHANNEL
import com.udacity.activity.MainActivity.Companion.NOTIFICATION_ID


fun NotificationManager.sendNotification(
    messageBody: String,
    applicationContext: Context,
){

    val contentIntent = Intent(applicationContext, MainActivity::class.java)
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