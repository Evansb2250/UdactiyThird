package com.udacity.util

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import com.udacity.LoadingButton
import com.udacity.environmentvariables.EnvironmentVariables.DOWNLOAD_ID
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
                DownloadManager.STATUS_PENDING -> {
                    println("pending")
                }
                DownloadManager.STATUS_PAUSED -> {
                    println("paused")
                }
                DownloadManager.STATUS_FAILED -> {
                    println("failed")
                    changeButtonContext(true, loadingButton)
                    break
                }
                DownloadManager.STATUS_RUNNING -> {
                    println("running")
                    //cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                    // cursor.getInt()
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
                    changeButtonContext(true, loadingButton)
                    break
                }

            }


        } finally {
            cursor.close()
        }


    }


//

//    var bytes_Downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
//    val fullSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))


}

suspend fun changeButtonContext(clickable: Boolean, loadingButton: LoadingButton) {
    withContext(Dispatchers.Main) {
        loadingButton.isContextClickable = clickable
    }
}


//    Log.i("DOWNLOADMANAGER", "${bytes_Downloaded}  full size ${fullSize}")
//    loadingButton.updateIsClickContext()
//  loadingButton.getDownloadProgress(32)


//        //create a euqry


