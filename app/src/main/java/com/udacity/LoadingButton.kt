package com.udacity

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.core.animation.doOnEnd
import com.udacity.download_util.createRequest
import com.udacity.environmentvariables.*
import kotlinx.coroutines.*
import kotlin.properties.Delegates

open class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var fragmentContext: Context? = context
    private val scope: CoroutineScope = CoroutineScope(CoroutineName("MyScope"))
    private val circleLayout = RectF()

    private val valueAnimator: ValueAnimator = ValueAnimator()
    private var buttonLabel = "Download"

    val loadButtonPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        Paint.Style.FILL
        color = getResources().getColor(R.color.loadButtonBarColor)
        Paint.Align.CENTER
        55.0f
        Typeface.create("", Typeface.BOLD)

    }


    val LoadingButtonLabel =  Paint(Paint.ANTI_ALIAS_FLAG).apply {
        Paint.Style.FILL_AND_STROKE
        color =  Color.BLACK
        textAlign = Paint.Align.CENTER
        textSize = 40.0f
        typeface= Typeface.create("", Typeface.BOLD)
        strokeWidth = 4f

    }

    val loadingCirclePaint =  Paint(Paint.ANTI_ALIAS_FLAG).apply {
        Paint.Style.FILL_AND_STROKE
        color =  getResources().getColor(R.color.yelloCircle)
        textAlign = Paint.Align.CENTER
        textSize = 40.0f
        typeface= Typeface.create("", Typeface.BOLD)
        strokeWidth = 4f

    }


    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Loading -> {
                scope.launch {
                    createRequest( fragmentContext!!, this@LoadingButton)
                }
            }
            ButtonState.Completed -> {
                stopAnimation()

            }

            ButtonState.Clicked -> {
                buttonState = ButtonState.Loading
                isClickable = false

            }
        }
    }

    private fun stopAnimation() {
        valueAnimator.repeatCount = 0
        valueAnimator.addUpdateListener {
            valueAnimator.doOnEnd {
                resetProgress()
                buttonLabel = "Download"
            }
        }
        isClickable = true
    }


    override fun performClick(): Boolean {
        if(ProjectData.getTitle() != null && ProjectData.getDesc() != null && ProjectData.getUrl() != null ){
            buttonState = ButtonState.Clicked
        }
        else{
            val toast: Toast = Toast.makeText(context.applicationContext, "Please select", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.BOTTOM, 0,1000)
            toast.show()
        }
        return super.performClick()
    }

    fun resetProgress() {
        progress = 0
        currentPercentage = 0f
        invalidate()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            canvas.drawRect(
                ((currentPercentage / 100) * width),
                heightSize.toFloat(),
                progress.toFloat(),
                0f,
                loadButtonPaint
            )

            canvas.drawArc(
                circleLayout, 270f,
                ((currentPercentage / 100) * 360f),
                true,
                loadingCirclePaint
            )

            canvas.drawText(
                buttonLabel,
                widthSize.toFloat() / 2,
                heightSize.toFloat() / 1.8f,
                LoadingButtonLabel
            )
        }
    }


    fun animateProgress() {
        valueAnimator.apply {
            setValues(valuesHolder)
            duration = 2000
            interpolator = DecelerateInterpolator()
            repeatCount = INFINITE
            buttonLabel = "We are Loading "
            addUpdateListener {
                currentPercentage = it.getAnimatedValue(PERCENTAGE_VALUE_HOLDER) as Float
                invalidate()
            }
        }
        valueAnimator.start()
    }

    fun downloadCompleted() {
        buttonState = ButtonState.Completed
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

        circleLayout.set(
            (widthSize/2.5).toFloat() + 210,
            (heightSize/2.5).toFloat()- 10f,
            (widthSize/2.5).toFloat() + 250f,
            (heightSize/2.5).toFloat() + 25
        )


        setMeasuredDimension(w, h)
    }



    companion object {
        private var widthSize = 0
        private var heightSize = 0
        private var progress = 0
        private var currentPercentage = 0F


        const val PERCENTAGE_VALUE_HOLDER = "percentage"
        private val valuesHolder = PropertyValuesHolder.ofFloat(
            PERCENTAGE_VALUE_HOLDER,
            0f,
            100f
        )
    }


}




