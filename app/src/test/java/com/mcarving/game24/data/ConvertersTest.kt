package com.mcarving.game24.data

import com.mcarving.game24.cards.Card
import com.mcarving.game24.cards.Card.Companion.DECK
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest{
    private val card1 = Card("♦", "3")
    private val card2 = Card("♥", "J")
    private val card3 = Card("♠", "8")

    private val cards = arrayListOf<Card>(DECK[1], DECK[2], DECK[3])

    @Test
    fun cardsToJsonString() {
        assertEquals()
    }

    @Test
    fun jsonStringToCards(){
        assertEquals()
    }
}