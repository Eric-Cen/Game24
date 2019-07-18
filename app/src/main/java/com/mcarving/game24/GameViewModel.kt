package com.mcarving.game24

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mcarving.game24.cards.Card
import kotlin.random.Random

class GameViewModel : ViewModel() {

    // list of poker cards for player 1

    // list of poker cards for player 2


    /******
     * Knuth Shuffle
     */

    fun shuffleDeck()
    {
        var cardDeck = Card.DECK.toMutableList()
        val rnd = Random

        for(x in (cardDeck.size-1) downTo 1){
            val index = rnd.nextInt(x + 1)
            // Simple swap
            val temp = cardDeck[index]
            cardDeck[index] = cardDeck[x]
            cardDeck[x] = temp
        }

        Log.d("CardViewModel", "shuffleDeck: ${Card.DECK.toString()}")
        Log.d("CardViewModel", "shuffleDeck: ${cardDeck.toString()}")
    }




}