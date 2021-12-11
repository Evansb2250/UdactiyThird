package com.udacity

import android.animation.ValueAnimator
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.udacity.util.createRequest
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0
    private var progress = 0

    private var title: String = ""
    private var desc: String = ""
    private var url: String = ""
    private var fragmentContext: Context? = null
    private val scope:CoroutineScope =  CoroutineScope(CoroutineName("MyScope"))

    private val valueAnimator = ValueAnimator()
    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Loading -> {
                val downloadManager = fragmentContext?.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                scope.launch {
                        createRequest(title, desc, url, fragmentContext!!, this@LoadingButton)
                }

            }
            ButtonState.Completed -> {
                println("downLoad finished")
            }

            ButtonState.Clicked -> {
                println("Clicked will start loading")
                buttonState = ButtonState.Loading

            }

        }
    }


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.RED
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }


    fun updateIsClickContext() {
        buttonState = ButtonState.Completed
    }


    suspend fun getDownloadProgress(progress: Double) {
        paint.color = Color.YELLOW
       println("Percentage $progress")


        withContext(Dispatchers.Main){
            valueAnimator.addUpdateListener {
                @Override
                fun onAnimationUpdate(animation: ValueAnimator){
                    val animatedValue = animation.getAnimatedValue().toString().toInt()

                }
            }

        }


        invalidate()
    }


    fun startDownload(title: String?, desc: String?, url: String?, fragContext: Context) {
        if (title != null && desc != null && url != null && fragContext != null) {
            this.title = title
            this.desc = desc
            this.url = url
            this.fragmentContext = fragContext
            buttonState = ButtonState.Clicked

        }


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            canvas.drawPaint(paint)
        }
    }


    /** TODO Add clickable function **/
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }


}


