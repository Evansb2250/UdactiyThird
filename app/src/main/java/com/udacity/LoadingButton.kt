package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet
import android.view.View
import com.udacity.environmentvariables.ProjectData
import com.udacity.download_util.createRequest
import kotlinx.coroutines.*
import kotlin.properties.Delegates

open class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val loadingButtonCanvas:Canvas = Canvas()
    private var widthSize = 0
    private var heightSize = 0
    private var progress = 0

    private var title: String = ""
    private var desc: String = ""
    private var url: String = ""
    private var fragmentContext: Context? = context
    private val scope: CoroutineScope = CoroutineScope(CoroutineName("MyScope"))

    private lateinit var valueAnimator:ValueAnimator

    val path = Path()
    val circle = RectF()




    private val buttonPaintLoaderStyle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }


    private val buttonPaintBaseStyle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.RED
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    private val loadingCirclePaintStyle = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }





    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Loading -> {
                scope.launch {
                 createRequest(title, desc, url, fragmentContext!!, this@LoadingButton)

                    withContext(Dispatchers.Main){
                        buttonState = ButtonState.Completed
                    }

                }
            }
            ButtonState.Completed -> {
                isClickable = true
            }

            ButtonState.Clicked -> {
                buttonState = ButtonState.Loading
                isClickable = false

            }
        }
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



    suspend fun getDownloadProgress(p1: Double) {


//        withContext(Dispatchers.Main) {
//            if(progress != widthSize && progress < widthSize){
//                progress = progress.plus(p1.toInt())
//                paint2.color = Color.YELLOW
//            }else{
//               resetProgress()
//            }
//
//
//            invalidate()
//
//        }

    }

   suspend fun resetProgress(){
        withContext(Dispatchers.Main) {
            buttonPaintLoaderStyle.color = Color.RED
            progress = 0
            invalidate()
        }

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null) {
            canvas.drawPaint(buttonPaintBaseStyle)

            canvas.drawRect(0f,heightSize.toFloat(),progress.toFloat(),0f, buttonPaintLoaderStyle )
            canvas.drawCircle(widthSize.toFloat() - 50, (heightSize/2).toFloat() ,40f,loadingCirclePaintStyle)

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

//
//    class CircleAnimator : ValueAnimator.AnimatorUpdateListener{
//        override fun onAnimationUpdate(animation: ValueAnimator?) {
//            //view place here
//
//
//        }
//
//    }


}




