package com.mcarving.game24.util

import android.util.Log
import com.mcarving.game24.cards.Card
import kotlin.random.Random

class Utils {

    companion object {
        /******
         * Knuth Shuffle
         */

        fun shuffleCards(cards : List<Card>) : MutableList<Card> {
            var shuffledCards = cards.toMutableList()
            val rnd = Random

            for(x in (shuffledCards.size-1) downTo 1){
                val index = rnd.nextInt(x + 1)
                // Simple swap
                val temp = shuffledCards[index]
                shuffledCards[index] = shuffledCards[x]
                shuffledCards[x] = temp
            }


            Log.d("Utils", "shuffleDeck before: ${cards.toString()}")
            Log.d("Utils", "shuffleDeck: after: ${shuffledCards.toString()}")

            return shuffledCards
        }
    }

}