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

    var twoCards : MutableList<Card>,

    var cardIndex : Int

    ) {
    companion object {
        const val TABLE_NAME = "cards"
    }
}