package com.mcarving.game24.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mcarving.game24.cards.Card


@Entity(tableName = PlayCards.TABLE_NAME)
data class PlayCards(
    @PrimaryKey
    var id : Int,

    var cardsInHand : MutableList<Card>,

    var turn : Boolean = false,

    // The selected two cards to play the 24 Card game
    var twoCards : MutableList<Card>,

    // pointer index used to draw the new two cards
    var cardIndex : Int

    ) {

    companion object {
        const val TABLE_NAME = "cards"
    }
}