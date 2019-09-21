package com.mcarving.game24

import com.mcarving.game24.cards.Card
import com.mcarving.game24.data.PlayCards

object TestData {
    val card1 = Card("♦", "3")
    val card2 = Card("♥", "J")
    val card3 = Card("♠", "8")

    val jsonOutput = "[\"3 ♦\",\"J ♥\",\"8 ♠\"]"

    val jsonInput = "[\"1 ♥\",\"2 ♥\",\"3 ♥\"]"

    val card4 = Card("♥", "1")
    val card5 = Card("♥", "2")
    val card6 = Card("♥", "3")


    val cardsInHand1 = arrayListOf<Card>(card1, card2, card3)

    val cardsInHand2 = arrayListOf<Card>(card4, card5, card6)

    val twoCards1 = arrayListOf<Card>(cardsInHand1[0], cardsInHand1[1])
    val twoCards2 = arrayListOf<Card>(cardsInHand2[0], cardsInHand2[1])


    val playCards1 = PlayCards(1, cardsInHand1, true, twoCards1, 1)
    val playCards2 = PlayCards(2, cardsInHand2, true, twoCards2, 1)

}