package com.udacity.activity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.udacity.R
import com.udacity.environmentvariables.ProjectData
import com.udacity.receiver.DownloadReceiver
import kotlinx.android.synthetic.main.activity_main.*

private val GLIDE_URL = "https://github.com/bumptech/glide/archive/refs/heads/master.zip"
private val GLIDE_TITLE = "Downloading Glide"
private val GLIDE_DESC = "Glide"

private val PROJECT_STARTER_URL =
    "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/refs/heads/master.zip"
private val PROJECT_Starter_Title = "Downloading Project Starter"
private val PROJECT_Starter_DESC = "Project Starter"

private val RETROFIT_URL =
    "https://github.com/square/retrofit/archive/refs/heads/master.zip"
private val RETROFIT_TITLE = "Downloading Retrofit"
private val RETROFIT_DESC = "Retrofit"


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(DownloadReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        createChannel("DOWNLOAD_CHANNEL", "Testing to see if channel is created")
        val radioButtonGroup = findViewById<RadioGroup>(R.id.button_group)




        val glideButton = findViewById<RadioButton>(R.id.glideButton)
        val loadAppButton = findViewById<RadioButton>(R.id.loadApp)
        val retrofitButton = findViewById<RadioButton>(R.id.retrofit)

        glideButton.setOnClickListener(this)
        loadAppButton.setOnClickListener(this)
        retrofitButton.setOnClickListener(this)

    }


    private fun createChannel(id: String, name: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                id,
                name,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.enableVibration(true)
            notificationChannel.description = "THIS IS A TEST"

            val notificationManager = this.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


    companion object {
        const val CHANNEL = "DOWNLOAD_CHANNEL"
        const val NOTIFICATION_ID = 0
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.glideButton -> {
                    ProjectData.changeProjectDetails(
                        GLIDE_TITLE,
                        GLIDE_DESC,
                        GLIDE_URL
                    )
                }
                R.id.loadApp -> {
                    ProjectData.changeProjectDetails(
                        PROJECT_Starter_Title,
                        PROJECT_Starter_DESC, PROJECT_STARTER_URL
                    )
                }
                R.id.retrofit -> {
                    ProjectData.changeProjectDetails(
                        RETROFIT_TITLE,
                        RETROFIT_DESC,
                        RETROFIT_URL
                    )
                }
            }
        }
    }


}
