package com.mcarving.game24.cards

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.mcarving.game24.R

/** Inflates and populates a [View] representing a [Card] */
class CardView : FrameLayout {
    private lateinit var _view : View
    private lateinit var _textSuite: TextView
    private lateinit var _textCorner1: TextView
    private lateinit var _textCorner2: TextView

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
        _view = LayoutInflater.from(context).inflate(R.layout.item_card_layout, this, true)
        _textSuite = _view.findViewById(R.id.label_center)
        _textCorner1 = _view.findViewById(R.id.label_top)
        _textCorner2 = _view.findViewById(R.id.label_bottom)

    }

    fun initSize(size: Float){

        //width = size
        this.invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)

        val height = displayMetrics.heightPixels
        val width  = displayMetrics.widthPixels

        var desiredWidth : Int
        var desiredHeight : Int

        if(height < width) { // in landscape mode
            desiredWidth = width / 6
            desiredHeight = desiredWidth * 7 / 5
        } else { // in portrait mode
            desiredWidth = width / 3
            desiredHeight = desiredWidth * 7 / 5
        }

        setMeasuredDimension(desiredWidth, desiredHeight)
    }


    /**
     * Updates the _view to represent the passed in card
     */
    fun bind(card: Card){
        _textSuite.text = card.suit
        _view.setBackgroundResource(R.color.blue_300)

        val cornerLabel = card.cornerLabel
        _textCorner1.text = cornerLabel
        _textCorner2.text = cornerLabel
    }
}