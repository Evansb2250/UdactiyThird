package com.udacity.activity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.udacity.R
import com.udacity.receiver.DownloadReceiver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(DownloadReceiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        createChannel("DOWNLOAD_CHANNEL", "Testing to see if channel is created")
        val radioButtonGroup = findViewById<RadioGroup>(R.id.button_group)



        custom_button.setOnClickListener{

            val radioButtonID = radioButtonGroup.getCheckedRadioButtonId()

            when(radioButtonID){
                R.id.radioButton3->  custom_button.startDownload(RETROFIT_TITLE,
                    RETROFIT_DESC, RETROFIT_URL, this)

                R.id.radioButton2-> custom_button.startDownload(PROJECT_Starter_Title,
                    PROJECT_Starter_DESC, PROJECT_STARTER_URL,
                    this)

                R.id.radioButton->  custom_button.startDownload(GLIDE_TITLE, GLIDE_DESC, GLIDE_URL,
                    this)
                else-> println("nothing selected")
            }



        }



    }



    private fun createChannel(id:String, name:String){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
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
        private val GLIDE_URL = "https://github.com/bumptech/glide/archive/refs/heads/master.zip"
        private val GLIDE_TITLE = "Downloading Glide"
        private val GLIDE_DESC="Glide"

        private val PROJECT_STARTER_URL = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/refs/heads/master.zip"
        private val PROJECT_Starter_Title = "Downloading Project Starter"
        private val PROJECT_Starter_DESC="Project Starter"

        private val RETROFIT_URL = "https://github.com/square/retrofit/archive/refs/heads/master.zip"
        private val RETROFIT_TITLE = "Downloading Retrofit"
        private val RETROFIT_DESC="Retrofit"

        const val CHANNEL = "DOWNLOAD_CHANNEL"
        const val NOTIFICATION_ID = 0

    }



}
