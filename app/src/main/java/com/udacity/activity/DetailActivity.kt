package com.udacity.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.udacity.R
import com.udacity.environmentvariables.DownloadStatus


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        Intent.FLAG_ACTIVITY_CLEAR_TASK

        val intent = getIntent()
        val data = intent.getExtras()?.get(DownloadStatus)
        val fileName = intent.getExtras()?.get("FileName")
        val fileNameTV = findViewById<TextView>(R.id.fileNameTV)

        val okayButton = findViewById<Button>(R.id.okayButton)


        okayButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        fileName?.let { it ->
            if (it is String) {
                fileNameTV.setText(it)
                fileNameTV.setTextColor(Color.BLUE)
            }

        }

        val lazyLabel = findViewById<TextView>(R.id.statusLabel)


        when (data) {
            true -> {
                lazyLabel.setText("Sucess")
                lazyLabel.setTextColor(Color.BLUE)
            }
            false -> {
                lazyLabel.setText("Failed")
                lazyLabel.setTextColor(Color.RED)

            }
        }
    }
}

