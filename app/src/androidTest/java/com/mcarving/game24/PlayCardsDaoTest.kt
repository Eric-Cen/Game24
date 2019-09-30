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
    private lateinit var database: CardsDatabase
    private lateinit var testData: AndroidTestData

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Using an in-memory database because the information store here disappears after test
        database = Room.inMemoryDatabaseBuilder(
            context,
            CardsDatabase::class.java
        )
            // allowing main thread queries, just for testing
            .allowMainThreadQueries()
            .build()
        playCardsDao = database.playCardDao()

        testData = AndroidTestData
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }


    // finish test Dao and create repository
    @Test
    @Throws(IOException::class)
    fun insertAndGetPlayCards() {
        val expectedPlay2 = testData.playCards2
        Timber.d(expectedPlay2.cardsInHand.toString())
        Timber.d(expectedPlay2.twoCards.toString())
        playCardsDao.insert(expectedPlay2)

        val playerCards = playCardsDao.getPlayCards(2)
        playerCards?.apply { Timber.d("after cardsInHand = ", cardsInHand.toString()) }
        playerCards?.apply { Timber.d("after twoCards = ", twoCards.toString()) }

        assertThat("The id should be equal", playerCards?.id, equalTo(expectedPlay2.id))
        assertThat(
            "The size of cards should be equal", playerCards?.cardsInHand?.size,
            equalTo(expectedPlay2.cardsInHand.size)
        )

        assertThat(playerCards?.toString(), equalTo(expectedPlay2.toString()))
    }


    @Test
    @Throws(IOException::class)
    fun insertAndUpdateCardsInHand() {
        playCardsDao.insert(testData.playCards2)

        val player2cardsInHand = playCardsDao.getPlayCards(2)?.cardsInHand
        player2cardsInHand?.add(testData.card1)
        playCardsDao.setCardsInHand(2, player2cardsInHand!!.toList())

        val newCards = playCardsDao.getPlayCards(2)?.cardsInHand

        assertThat(newCards.toString(), equalTo(player2cardsInHand.toString()))
    }

    @Test
    @Throws(IOException::class)
    fun insertAndUpdateTwoCards() {
        playCardsDao.insert(testData.playCards2)
        val twoCards = playCardsDao.getPlayCards(2)?.twoCards
        twoCards?.set(1, testData.card1)
        playCardsDao.setTwoCards(2, twoCards!!.toList())

        val newTwoCards = playCardsDao.getPlayCards(2)?.twoCards

        assertThat(
            "two cards should be equal", newTwoCards?.toString(),
            equalTo(twoCards.toString())
        )
    }

    @Test
    @Throws(IOException::class)
    fun insertAndUpdatePlayerTurnStatus() {
        playCardsDao.insert(testData.playCards1)

        val status = playCardsDao.getPlayerTurnStatus(1)
        playCardsDao.setPlayerTurnStatus(1, !status)
        val newStatus = playCardsDao.getPlayerTurnStatus(1)

        assertThat("Turns should be equal", !newStatus, equalTo(testData.playCards1.turn))
    }


    @Test
    @Throws(Exception::class)
    fun deleteAll() {
        playCardsDao.insert(testData.playCards1)
        playCardsDao.insert(testData.playCards2)

        playCardsDao.deleteAll()

        val allPlayCards = playCardsDao.getAllPlayCards()

        assertTrue(allPlayCards.isEmpty())
    }

}