package com.udacity.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.udacity.R
import com.udacity.environmentvariables.DownloadStatus

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val intent = getIntent()

        val data = intent.getExtras()?.get(DownloadStatus)
        val fileName = intent.getExtras()?.get("FileName")
        val fileNameTV = findViewById<TextView>(R.id.fileNameTV)


        fileName?.let { it ->
            if(it is String){
                fileNameTV.setText(it)
            }

        }

        val lazyLabel = findViewById<TextView>(R.id.statusLabel)

        when(data){
            true-> lazyLabel.setText("Sucess")
            false-> lazyLabel.setText("Failed")
            else -> lazyLabel.setText("WHAT THE HELL IS THIS !!")
        }
    }
}

