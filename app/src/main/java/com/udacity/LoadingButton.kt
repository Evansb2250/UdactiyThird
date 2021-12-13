package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import com.udacity.environmentvariables.ProjectData
import com.udacity.download_util.createRequest
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
    private var fragmentContext: Context? = context
    private val scope: CoroutineScope = CoroutineScope(CoroutineName("MyScope"))

    private val valueAnimator = ValueAnimator()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.RED
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }



    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Loading -> {
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





    fun updateIsClickContext() {
        buttonState = ButtonState.Completed
    }


    override fun performClick(): Boolean {
        val nulValueToEmptySpace = ""
        this.title = ProjectData.getTitle()?:nulValueToEmptySpace
        this.desc = ProjectData.getDesc()?:nulValueToEmptySpace
        this.url = ProjectData.getUrl()?:nulValueToEmptySpace

        if (title != nulValueToEmptySpace && desc != nulValueToEmptySpace && url != nulValueToEmptySpace) {
             buttonState = ButtonState.Clicked
        }
        return super.performClick()

    }


    suspend fun getDownloadProgress(progress: Double) {
        withContext(Dispatchers.Main) {
            valueAnimator.addUpdateListener {
                @Override
                fun onAnimationUpdate(animation: ValueAnimator) {
                    val animatedValue = animation.getAnimatedValue().toString().toInt()

                }
            }

        }
        invalidate()
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


