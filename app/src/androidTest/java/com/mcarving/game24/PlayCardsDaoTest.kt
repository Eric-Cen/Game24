package com.mcarving.game24

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mcarving.game24.data.source.local.CardsDatabase
import com.mcarving.game24.data.source.local.PlayCardsDao
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PlayCardsDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var playCardsDao: PlayCardsDao
    private lateinit var database : CardsDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Using an in-memory database because the information store here disappears after test
        database = Room.inMemoryDatabaseBuilder(context,
            CardsDatabase::class.java)
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
        playCardsDao = database.playCardDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        database.close()
    }


    @Test
    @Throws(IOException::class)
    fun insertAndGetPlayCards(){
        playCardsDao.insert(TestData.playCards2)

        val playerCards = playCardsDao.getPlayCards(2)

    }


    @Test
    @Throws(IOException::class)
    fun insertAndUpdateCardsInHand(){
        playCardsDao.insert(TestData.playCards2)

        val cardsInHand = playCardsDao.getPlayCards(2).cardsInHand

    }

    @Test
    @Throws(IOException::class)
    fun insertAndUpdateTwoCards(){
        playCardsDao.insert(TestData.playCards2)
        val twoCards = playCardsDao.getPlayCards(2).twoCards
    }

    @Test
    @Throws(IOException::class)
    fun insertAndUpdatePlayerTurnStatus(){
        playCardsDao.insert(TestData.playCards1)

        val status = playCardsDao.getPlayerTurnStatus(1)

    }

    @Test
    @Throws(IOException::class)
    fun insertAndUpdateSelectedCardIndex(){
        playCardsDao.insert(TestData.playCards1)
        val index = playCardsDao.getSelectedCardIndex(1)

    }

    @Test
    @Throws(Exception::class)
    fun deleteAll(){
        playCardsDao.insert(TestData.playCards1)
        playCardsDao.insert(TestData.playCards2)

        playCardsDao.deleteAll()

        val allPlayCards = playCardsDao.getAllPlayCards()

        assertTrue(allPlayCards.isEmpty())

    }

}