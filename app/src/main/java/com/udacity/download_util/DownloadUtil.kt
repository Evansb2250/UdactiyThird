package com.udacity.download_util

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.udacity.LoadingButton
import com.udacity.environmentvariables.DownloadStatus
import com.udacity.environmentvariables.EnvironmentVariables.DOWNLOAD_ID
import com.udacity.environmentvariables.ProjectData
import com.udacity.environmentvariables.SuccessMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun createRequest(
    fragContext: Context,
    loadingButton: LoadingButton
) {
    val url = ProjectData.getUrl()
    val title = ProjectData.getTitle()
    val desc = ProjectData.getDesc()

    if(url != null && title != null && desc != null) {
        var isFileDownloading = false
        val downloadManager = fragContext?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        //Reset
        ProjectData.clearVariables()
        DOWNLOAD_ID = downloadManager.enqueue(
            buildDownloadRequest(
                url,
                title,
                desc
            )
        )// enqueue puts the download request in the queue.

        while (true) {
            val query = DownloadManager.Query()
            query.setFilterById(DOWNLOAD_ID)
            val cursor = downloadManager.query(query)

            try {
                cursor.moveToFirst()
                val status = cursor.getInt(cursor.getColumnIndex((DownloadManager.COLUMN_STATUS)))
                when (status) {
                    DownloadManager.STATUS_FAILED -> {
                        withContext(Dispatchers.Main) {
                            updateDownloadButton(loadingButton)
                        }
                        sendFileDataToDownloadReceiver(title, desc, false, fragContext)
                        break
                    }
                    DownloadManager.STATUS_RUNNING -> {
                        if (!isFileDownloading) {
                            isFileDownloading = true
                            withContext(Dispatchers.Main) {
                                loadingButton.animateProgress()
                            }
                        }
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        withContext(Dispatchers.Main) {
                            updateDownloadButton(loadingButton)
                        }
                        sendFileDataToDownloadReceiver(title, desc, true, fragContext)
                        break
                    }
                }
            } finally {
                cursor.close()
            }


        }
    }

}

private fun buildDownloadRequest(
    url: String,
    title: String,
    desc: String
): DownloadManager.Request? {
    val request = DownloadManager.Request(Uri.parse(url))
        .setTitle(title)
        .setDescription(desc)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        .setRequiresCharging(false)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)
    return request
}



private fun updateDownloadButton(loadingButton: LoadingButton) {
    loadingButton.downloadCompleted()
    loadingButton.resetProgress()
}

private fun sendFileDataToDownloadReceiver(
    title: String,
    desc: String,
    status: Boolean,
    fragContext: Context
) {
    Intent().also { intent ->
        intent.setAction(SuccessMessage)
        intent.putExtra("title", "${title} ${desc}")
        intent.putExtra(DownloadStatus, status)
        fragContext.sendBroadcast(intent)
    }
}




