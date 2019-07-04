package com.mcarving.game24.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mcarving.game24.R

/** Inflates and populates a [View] representing a [Card] */
class CardView(layoutInflater: LayoutInflater, container: ViewGroup?){
    val view: View = layoutInflater.inflate(R.layout.item_card_layout, container, false)
    private val textSuite: TextView
    private val textCorner1: TextView
    private val textCorner2: TextView

    init{
        textSuite = view.findViewById(R.id.label_center)
        textCorner1 = view.findViewById(R.id.label_top)
        textCorner2 = view.findViewById(R.id.label_bottom)
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