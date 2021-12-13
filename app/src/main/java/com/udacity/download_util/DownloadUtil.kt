package com.udacity.download_util

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.udacity.LoadingButton
import com.udacity.environmentvariables.DownloadStatus
import com.udacity.environmentvariables.EnvironmentVariables.DOWNLOAD_ID
import com.udacity.environmentvariables.ProjectData
import com.udacity.environmentvariables.SuccessMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun createRequest(
    title: String,
    desc: String,
    url: String,
    fragContext: Context,
    loadingButton: LoadingButton
) {

    changeButtonContext(false, loadingButton)
    var percentage = 0.0

    val request = DownloadManager.Request(Uri.parse(url))
        .setTitle(title)
        .setDescription(desc)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        .setRequiresCharging(false)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)


    val downloadManager = fragContext?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    DOWNLOAD_ID = downloadManager.enqueue(request)// enqueue puts the download request in the queue.


    while (true) {
        val query = DownloadManager.Query()
        query.setFilterById(DOWNLOAD_ID)
        val cursor = downloadManager.query(query)

        try {
            cursor.moveToFirst()
            val status = cursor.getInt(cursor.getColumnIndex((DownloadManager.COLUMN_STATUS)))
            when (status) {
                DownloadManager.STATUS_FAILED -> {
                    DOWNLOAD_ID = -1
                    sendFileDataToDownloadReceiver(title, desc, false, fragContext)
                    changeButtonContext(true, loadingButton)
                    break
                }
                DownloadManager.STATUS_RUNNING -> {
                    println("running")
                    val total_Download_Size = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    val total_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))

                    if (total_Download_Size != -1) {
                        percentage = (total_downloaded / total_Download_Size.toDouble())
                        // loadingButton.getDownloadProgress(percentage)
                        println("Percentage from button Messenger ${percentage}")
                    }

                }
                DownloadManager.STATUS_SUCCESSFUL -> {
                    println("succeeded")
                    sendFileDataToDownloadReceiver(title, desc, true, fragContext)

                    break
                }
            }
        } finally {
            cursor.close()
        }


    }




}

private fun sendFileDataToDownloadReceiver(
    title: String,
    desc: String,
    status:Boolean,
    fragContext: Context
) {
    Intent().also { intent ->
        intent.setAction(SuccessMessage)
        intent.putExtra("title", "${title} ${desc}")
        intent.putExtra(DownloadStatus,status)
        fragContext.sendBroadcast(intent)
    }
}

suspend fun changeButtonContext(clickable: Boolean, loadingButton: LoadingButton) {
    withContext(Dispatchers.Main) {
        loadingButton.isContextClickable = clickable
    }
}


