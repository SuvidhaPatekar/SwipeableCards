package demo.swipeablecards

import android.app.Activity
import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent

class SwappableCardView(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {

    init {
        //xCord and yCord - Position of view before touch
        var startX = 0
        var startY = 0

        //xCord and yCord - Position of view after touch
        var xCord = 0
        var yCord = 0

        var toRemove = false

        //Get window width and window height to calculate horizontal and vertical center of the screen
        var windowWidth = 0
        var windowHeight = 0
        var screenCenterHorizontal = 0
        var screenCenterVertical = 0

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        windowWidth = displayMetrics.widthPixels
        windowHeight = displayMetrics.heightPixels
        screenCenterHorizontal = windowWidth / 2
        screenCenterVertical = windowHeight / 2

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

                    //Remove card from left or right side checking horizontal center
                    toRemove = if (xCord >= screenCenterHorizontal) {
                        if (xCord > screenCenterHorizontal + screenCenterHorizontal / 2) {
                            xCord > windowWidth - screenCenterHorizontal / 4
                        } else {
                            false
                        }
                    } else {
                        if (xCord < screenCenterHorizontal / 2) {
                            xCord < screenCenterHorizontal / 4
                        } else {
                            false
                        }
                    }

                    //Remove card from top or bottom side checking vertical center only if toRemove is false
                    if (!toRemove) {
                        toRemove = if (yCord >= screenCenterVertical) {
                            if (yCord > screenCenterVertical + screenCenterVertical / 2) {
                                yCord > windowWidth - screenCenterVertical / 4
                            } else {
                                false
                            }
                        } else {
                            if (yCord < screenCenterVertical / 2) {
                                yCord < screenCenterVertical / 4
                            } else {
                                false
                            }
                        }
                    }
                }

                MotionEvent.ACTION_UP -> {
                    xCord = motionEvent.rawX.toInt()
                    yCord = motionEvent.rawY.toInt()

                    if (toRemove) {
                        //Swipe card
                        Log.d("Remove card", "Inside to remove method")
                    } else {
                        //init position
                        x = 0f
                        y = 0f
                    }
                }
            }
            return@setOnTouchListener true
        }
    }
}