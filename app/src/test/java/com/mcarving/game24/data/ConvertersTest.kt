package com.mcarving.game24.data

import com.mcarving.game24.TestData
import com.mcarving.game24.cards.Card
import com.mcarving.game24.cards.Card.Companion.DECK
import com.mcarving.game24.data.source.local.ListConverter
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest{


    @Test
    fun cardsToJsonString() {
        val cards = arrayListOf<Card>(TestData.card1, TestData.card2, TestData.card3)
        val newString = ListConverter().listToJson(cards)

        assertEquals(TestData.jsonOutput, newString)
    }

    @Test
    fun jsonStringToCards(){
        val newCards = ListConverter().jsonToList(TestData.jsonInput)

        assertEquals(TestData.card4.toString(), newCards[0].toString())
        assertEquals(TestData.card5.toString(), newCards[1].toString())
        assertEquals(TestData.card6.toString(), newCards[2].toString())
    }
}