package com.mcarving.game24.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mcarving.game24.util.Utils
import com.mcarving.game24.cards.Card
import com.mcarving.game24.data.source.local.ListConverter
import com.mcarving.game24.data.source.local.PlayCardsDao
import java.util.concurrent.Executors

@Database(entities = arrayOf(PlayCards::class), version = 1)
@TypeConverters(ListConverter::class)
abstract class CardsDatabase : RoomDatabase(){

    abstract fun playCardDao() : PlayCardsDao

    /**
     *  Reset and populate cards in hand when the Play Game button is pressed
     */
    fun populateInitialData(){
        Executors.newSingleThreadScheduledExecutor().execute {
            beginTransaction()
            try {
                // reset card data for the players
                for(i in 0..1) {
                    playCardDao().setCardsInHand(i, arrayListOf<Card>())
                    playCardDao().setTwoCards(i, arrayListOf<Card>())
                    playCardDao().setPlayerTurnStatus(i, false)
                    playCardDao().setSelectedCardInex(i, -1)

                }

                // initialize data
            } catch (){
                val cardDeck = Utils.shuffleCards(Card.DECK)
                val(a, b) = cardDeck.partition { acard ->
                    cardDeck.indexOf(acard) % 2 == 0
                }

                // set initial data for player 1
                playCardDao().setCardsInHand(0, a)
                playCardDao().setTwoCards(0, arrayListOf(a[0], a[1]))
                playCardDao().setSelectedCardInex(0, 1)

                // set initial data for player 2
                playCardDao().setCardsInHand(1, b)
                playCardDao().setTwoCards(1, listOf(b[0], b[1]))
                playCardDao().setSelectedCardInex(1, 1)

            }
        }
    }


}