package demo.swipeablecards

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_single_card.view.*

class MainActivity : AppCompatActivity(), SwipeableCardView.SwipeActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //onClickListener on Refresh image will reload data
        ivRefresh.setOnClickListener({
            ivRefresh.visibility = View.GONE
            swipeCards()
        })

        swipeCards()
    }

    private fun swipeCards() {
        val margin = 80
        for (i in 10 downTo 1) {

            //inflate card view and set layout parameters
            val inflate = this@MainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val singleCardView = inflate.inflate(R.layout.layout_single_card, null)

            val layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            layoutParams.setMargins(margin, margin, margin, margin)
            singleCardView.layoutParams = layoutParams

            //Set tag to card
            val tvName = singleCardView.findViewById(R.id.tvTag) as TextView
            tvName.text = i.toString()

            //Set swipeActionListener to view
            singleCardView.swCardView.setSwipeActionListener(this)

            //Adding child to main view
            rlMain.addView(singleCardView)
        }
    }

    override fun removeCard(view: SwipeableCardView) {
        //Remove child view from parent view
        rlMain.removeView(view)

        //if there is no child then make refresh button visible
        if (rlMain.childCount == 0) {
            ivRefresh.visibility = View.VISIBLE
        }
    }
}
