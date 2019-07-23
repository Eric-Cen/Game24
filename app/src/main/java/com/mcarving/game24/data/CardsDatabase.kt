package com.mcarving.game24.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mcarving.game24.cards.Card
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
                // reset
                for(i in 0..1) {
                    playCardDao().setCardsInHand(i, arrayListOf<Card>())
                    playCardDao().setTwoCards(i, arrayListOf<Card>())
                    playCardDao().setPlayerTurnStatus(i, false)
                    playCardDao().setSelectedCardInex(i, -1)

                }

                // initialize data
            }
        }
    }
}