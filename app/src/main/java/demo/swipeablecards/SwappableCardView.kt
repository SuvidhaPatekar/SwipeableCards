package demo.swipeablecards

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.MotionEvent

class SwappableCardView(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {

    init {
        //xCord and yCord - Position of view before touch
        var startX = 0
        var startY = 0

        //xCord and yCord - Position of view after touch
        var xCord = 0
        var yCord = 0


        setOnTouchListener { view, motionEvent ->

            xCord = motionEvent.rawX.toInt()
            yCord = motionEvent.rawY.toInt()

            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = motionEvent.x.toInt()
                    startY = motionEvent.y.toInt()
                }

                MotionEvent.ACTION_MOVE -> {
                    xCord = motionEvent.rawX.toInt()
                    yCord = motionEvent.rawY.toInt()

                    x = (xCord - startX).toFloat()
                    y = (yCord - startY).toFloat()
                }

                MotionEvent.ACTION_UP -> {
                    xCord = motionEvent.rawX.toInt()
                    yCord = motionEvent.rawY.toInt()

                }
            }
            return@setOnTouchListener true
        }
    }
}