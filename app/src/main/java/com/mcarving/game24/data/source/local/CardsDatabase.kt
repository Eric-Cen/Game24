package com.mcarving.game24.data.source.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mcarving.game24.util.Utils
import com.mcarving.game24.cards.Card
import com.mcarving.game24.data.PlayCards
import com.mcarving.game24.util.ioThread
import timber.log.Timber
import java.util.concurrent.Executors

@Database(entities = arrayOf(PlayCards::class), version = 1)
@TypeConverters(ListConverter::class)
abstract class CardsDatabase : RoomDatabase() {

    abstract fun playCardDao(): PlayCardsDao

    /**
     *  Reset and populate cards in hand when the Play Game button is pressed
     */
    fun populateInitialData() {

        var player1 = playCardDao().getPlayCards(PLAYERONE)
        var player2 = playCardDao().getPlayCards(PLAYERTWO)


        //TODO
        // need to use insert() to add row to table
        // then finish the database testing
        // then go for the answer UI with drag and release feature
        //if(player1?.cardsInHand?.isEmpty() && player2.cardsInHand.isEmpty()) {
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

            //retrive the new data
            player1 = playCardDao().getPlayCards(PLAYERONE)
            player2 = playCardDao().getPlayCards(PLAYERTWO)

        } finally {
            endTransaction()
        }
        //  } // else, do nothing since there is already data in there

        Timber.d("populateInitialData: player1's card # = %d", player1?.cardsInHand?.size)
        Timber.d("populateInitialData: player2's card # = %d", player2?.cardsInHand?.size)

    }

    companion object {
        const val PLAYERONE = 1
        const val PLAYERTWO = 2

        @Volatile
        private var INSTANCE: CardsDatabase? = null

        fun getDatabase(context: Context): CardsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    // it.populateInitialData()
                    INSTANCE = it
                }
            }


        private fun buildDatabase(context: Context): CardsDatabase {
            val sInstance = Room.databaseBuilder(
                context.applicationContext,
                CardsDatabase::class.java,
                "cards.db")
                // Create and pre-populate the database. See this article for more details:
                // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        ioThread {
                            //getDatabase(context.applicationContext).populateInitialData()

                        }
                    }
                })
                .build()

            return sInstance
        }
    }

}