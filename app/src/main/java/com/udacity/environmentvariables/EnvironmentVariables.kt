package com.udacity.environmentvariables

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface

object EnvironmentVariables {
    var DOWNLOAD_ID : Long  = 0
    const val CHANNEL_ID = "channel_Id"
    const val APP_NAME = ""
}


const val GLIDE_URL = "https://github.com/bumptech/glide/archive/refs/heads/master.zip"
const val GLIDE_TITLE = "Downloading Glide"
const val GLIDE_DESC = "Glide"

const val PROJECT_STARTER_URL = "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/refs/heads/master.zip"
const val PROJECT_Starter_Title = "Downloading Project Starter"
const val PROJECT_Starter_DESC = "Project Starter"

const val RETROFIT_URL = "https://github.com/square/retrofit/archive/refs/heads/master.zip"
const val RETROFIT_TITLE = "Downloading Retrofit"
const val RETROFIT_DESC = "Retrofit"

val SuccessMessage = "com.udacity.activity.intent.customDownloadReceiver"
val DownloadStatus = "customDownloaderStatus"
val download_success= "Download Succeeded"
val download_failed="Download Failed"



val buttonPaintLoaderStyle = getButtonPaintBaseStyle(
    Paint.Style.FILL,
    Color.BLUE,
    Paint.Align.CENTER,
    55.0f,
    Typeface.create("", Typeface.BOLD)
)

val buttonPaintBaseStyle = getButtonPaintBaseStyle(
    Paint.Style.FILL,
    Color.BLUE,
    Paint.Align.CENTER,
    55.0f,
    Typeface.create("", Typeface.BOLD)
)

val loadingCirclePaintStyle = getButtonPaintBaseStyle(
    Paint.Style.FILL_AND_STROKE,
    Color.YELLOW,
    Paint.Align.CENTER,
    55.0f,
    Typeface.create("", Typeface.BOLD)
)

val viewText = getButtonPaintBaseStyle(
    Paint.Style.FILL_AND_STROKE,
    Color.BLACK,
    Paint.Align.CENTER,
    55.0f,
    Typeface.create("", Typeface.NORMAL),
4f
)

val nullValueToEmptySpace = ""

private fun getButtonPaintBaseStyle(
    fill: Paint.Style,
    paintColor: Int,
    alignment: Paint.Align,
    sizeOfText: Float,
    customTypeFace: Typeface,
    textWidth:Float = 0f
): Paint {
    return Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = fill
        color = paintColor
        textAlign = alignment
        textSize = sizeOfText
        typeface = customTypeFace
        strokeWidth = textWidth
    }
}

