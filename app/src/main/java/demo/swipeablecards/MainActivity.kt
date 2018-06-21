package demo.swipeablecards

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_single_card.view.*

class MainActivity : AppCompatActivity(), SwappableCardView.SwipeActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeCards()
    }

    private fun swipeCards() {
        val margin = 80
        for (i in 10 downTo 1) {
            val inflate = this@MainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val singleCardView = inflate.inflate(R.layout.layout_single_card, null)

            val layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            layoutParams.setMargins(margin, margin, margin, margin)
            singleCardView.layoutParams = layoutParams

            val tvName = singleCardView.findViewById(R.id.tvTag) as TextView
            tvName.text = i.toString()

            singleCardView.swappableCardView.setSwipeActionListener(this)

            //Adding child to main view
            rlMain.addView(singleCardView)
        }
    }

    override fun removeCard(view: SwappableCardView) {
        rlMain.removeView(view)
    }
}
