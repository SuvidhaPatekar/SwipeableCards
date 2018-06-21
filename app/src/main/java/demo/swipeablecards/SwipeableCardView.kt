package demo.swipeablecards

import android.app.Activity
import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent

class SwipeableCardView(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {

    private lateinit var swipeActionListener: SwipeActionListener

    fun setSwipeActionListener(swipeActionListener: SwipeActionListener) {
        this.swipeActionListener = swipeActionListener
    }

    init {
        //xCord and yCord - Position of view before touch
        var startX = 0
        var startY = 0


        //xCord and yCord - Position of view after touch
        var xCord = 0
        var yCord = 0

        val margin = 80f
        var toRemove = false

        //Get window width and window height to calculate horizontal and vertical center of the screen

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val windowWidth = displayMetrics.widthPixels
        val windowHeight = displayMetrics.heightPixels
        val screenCenterHorizontal = windowWidth / 2
        val screenCenterVertical = windowHeight / 2

        setOnTouchListener { view, motionEvent ->

            xCord = motionEvent.rawX.toInt()
            yCord = motionEvent.rawY.toInt()

            cardElevation = 40f

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
                        //Rotate view clockwise
                        rotation = 12f

                        if (xCord > screenCenterHorizontal + screenCenterHorizontal / 2) {
                            xCord > windowWidth - screenCenterHorizontal / 4
                        } else {
                            false
                        }
                    } else {
                        //Rotate view antiClockWise
                        rotation = -12f

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
                        //Call remove card method to remove selected view
                        swipeActionListener.removeCard(this)
                    } else {
                        //init position
                        x = margin
                        y = margin
                        rotation = 0f
                    }
                }
            }
            return@setOnTouchListener true
        }
    }

    interface SwipeActionListener {
        fun removeCard(view: SwipeableCardView)
    }
}