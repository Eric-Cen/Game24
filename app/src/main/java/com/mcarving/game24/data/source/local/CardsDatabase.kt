package com.mcarving.game24.data.source.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mcarving.game24.util.Utils
import com.mcarving.game24.cards.Card
import com.mcarving.game24.data.PlayCards
import java.util.concurrent.Executors

@Database(entities = arrayOf(PlayCards::class), version = 1)
@TypeConverters(ListConverter::class)
abstract class CardsDatabase : RoomDatabase(){

    abstract fun playCardDao() : PlayCardsDao

    /**
     *  Reset and populate cards in hand when the Play Game button is pressed
     */
    fun populateInitialData(){
        val TAG = "CardsDatabase"
        Executors.newSingleThreadScheduledExecutor().execute {
            val cards4Player1 = playCardDao().getCardsInHand(0)
            val cards4Player2 = playCardDao().getCardsInHand(1)

            if(cards4Player1.isEmpty() && cards4Player2.isEmpty()) {
                beginTransaction()
                try {
                    // reset card data for the players
                    for (i in PLAYERONE..PLAYERTWO) {
                        playCardDao().setCardsInHand(i, arrayListOf<Card>())
                        playCardDao().setTwoCards(i, arrayListOf<Card>())
                        playCardDao().setPlayerTurnStatus(i, false)
                        playCardDao().setSelectedCardInex(i, -1)
                    }

                    // initialize data
                    val cardDeck = Utils.shuffleCards(Card.DECK)
                    val (a, b) = cardDeck.partition { acard ->
                        cardDeck.indexOf(acard) % 2 == 0
                    }

                    // set initial data for player 1
                    playCardDao().setCardsInHand(PLAYERONE, a)
                    playCardDao().setTwoCards(PLAYERONE, arrayListOf(a[0], a[1]))
                    playCardDao().setSelectedCardInex(PLAYERONE, 1)

                    // set initial data for player 2
                    playCardDao().setCardsInHand(PLAYERTWO, b)
                    playCardDao().setTwoCards(PLAYERTWO, listOf(b[0], b[1]))
                    playCardDao().setSelectedCardInex(PLAYERTWO, 1)

                } finally {
                    endTransaction()
                }
            } else {
                Log.d(TAG, "populateInitialData: player1's card # = " + cards4Player1.size)
                Log.d(TAG, "populateInitialData: player2's card # = " + cards4Player2.size)
            }
        }
    }

    companion object {
        const val PLAYERONE = 0
        const val PLAYERTWO = 1

        @Volatile
        private var INSTANCE : CardsDatabase? = null

        fun getDatabase(context: Context) : CardsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    it.populateInitialData()
                    INSTANCE = it
                }
            }


        private fun buildDatabase(context: Context) : CardsDatabase {
            val sInstance = Room.databaseBuilder(
                context.applicationContext,
                CardsDatabase::class.java,
                "cards.db")
                .build()

            return sInstance
        }
    }

}