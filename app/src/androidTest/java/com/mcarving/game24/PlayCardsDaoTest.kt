package com.mcarving.game24


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import com.mcarving.game24.data.source.local.CardsDatabase
import com.mcarving.game24.data.source.local.PlayCardsDao
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.io.IOException



@RunWith(AndroidJUnit4::class)
class PlayCardsDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var playCardsDao: PlayCardsDao
    private lateinit var database : CardsDatabase
    private lateinit var testData : AndroidTestData

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

        testData = AndroidTestData
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        database.close()
    }


    // finish test Dao and create repository
    @Test
    @Throws(IOException::class)
    fun insertAndGetPlayCards(){
        val playBefore = playCardsDao.getPlayCards(2)
        playBefore?.apply { Timber.d("cardsInHand =", cardsInHand.toString()) }
        playBefore?.apply {Timber.d("twoCards = ",  twoCards.toString() )}


        val play2 = testData.playCards2
        Timber.d(play2.cardsInHand.toString())
        Timber.d(play2.twoCards.toString())
        playCardsDao.insert(play2)


        val playerCards = playCardsDao.getPlayCards(2)
        playerCards?.apply {Timber.d("cardsInHand = ", cardsInHand.toString())}
        playerCards?.apply{Timber.d("twoCards = ", twoCards.toString())}

        assertThat("playCards should be equal", playerCards, equalTo(play2))


    }


//    @Test
//    @Throws(IOException::class)
//    fun insertAndUpdateCardsInHand(){
//        playCardsDao.insert(TestData.playCards2)
//
//        val cardsInHand = playCardsDao.getPlayCards(2).cardsInHand
//
//    }

//    @Test
//    @Throws(IOException::class)
//    fun insertAndUpdateTwoCards(){
//        playCardsDao.insert(TestData.playCards2)
//        val twoCards = playCardsDao.getPlayCards(2).twoCards
//    }

//    @Test
//    @Throws(IOException::class)
//    fun insertAndUpdatePlayerTurnStatus(){
//        playCardsDao.insert(TestData.playCards1)
//
//        val status = playCardsDao.getPlayerTurnStatus(1)
//
//    }

//    @Test
//    @Throws(IOException::class)
//    fun insertAndUpdateSelectedCardIndex(){
//        playCardsDao.insert(TestData.playCards1)
//        val index = playCardsDao.getSelectedCardIndex(1)
//
//    }

    @Test
    @Throws(Exception::class)
    fun deleteAll(){
        playCardsDao.insert(testData.playCards1)
        playCardsDao.insert(testData.playCards2)

        playCardsDao.deleteAll()

        val allPlayCards = playCardsDao.getAllPlayCards()

        assertTrue(allPlayCards.isEmpty())

    }

}