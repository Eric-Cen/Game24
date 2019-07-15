package com.mcarving.game24.cards

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.mcarving.game24.R
import java.util.jar.Attributes

/** Inflates and populates a [View] representing a [Card] */
class CardView : FrameLayout {
    private lateinit var view : View
    private lateinit var textSuite: TextView
    private lateinit var textCorner1: TextView
    private lateinit var textCorner2: TextView

    constructor(context: Context) : super(context, null){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init(context)
    }

    constructor(context: Context,
                attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init(context)
    }


    private fun init(context: Context){

        Log.d("CardView", "init: is in CardView init()")
        view = LayoutInflater.from(context).inflate(R.layout.item_card_layout, this, true)
        textSuite = view.findViewById(R.id.label_center)
        textCorner1 = view.findViewById(R.id.label_top)
        textCorner2 = view.findViewById(R.id.label_bottom)

    }

    fun initSize(size: Float){

        //width = size
        this.invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * Updates the view to represent the passed in card
     */
    fun bind(card: Card){
        textSuite.text = card.suit
        view.setBackgroundResource(R.color.blue_300)

        val cornerLabel = card.cornerLabel
        textCorner1.text = cornerLabel
        textCorner2.text = cornerLabel
    }
}