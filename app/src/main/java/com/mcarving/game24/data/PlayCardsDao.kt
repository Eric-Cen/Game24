package com.mcarving.game24.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.TypeConverters
import com.mcarving.game24.cards.Card

@Dao
interface PlayCardsDao {
    // Get player's cards in hand
    @TypeConverters(ListConverter::class)
    @Query("SELECT cardsInHand from " + PlayCards.TABLE_NAME
    + " WHERE id = :playerId")
    fun getCardsInHand(playerId: Int) : List<Card>

    // Set player's cards in hand
    @TypeConverters(ListConverter::class)
    @Query("UPDATE " + PlayCards.TABLE_NAME
    + " SET cardsInHand = :cards WHERE  id = :playerId")
    fun setCardsInHand(playerId: Int, cards : List<Card>)

    // Get player's 2 drawn cards
    @TypeConverters(ListConverter::class)
    @Query("SELECT twoCards from " + PlayCards.TABLE_NAME
    + " WHERE id = :playerId")
    fun getTwoCards(playerId: Int) : List<Card>

    // Set player's 2 drawn cards
    @TypeConverters(ListConverter::class)
    @Query("UPDATE " + PlayCards.TABLE_NAME
    + " SET twoCards = :cards WHERE id = :playerId")
    fun setTwoCards(playerId : Int, cards: List<Card>)


    // Get the current index of the 2 drawn cards
    @Query("SELECT cardIndex FROM " + PlayCards.TABLE_NAME
            + " WHERE id = :playerId")
    fun getSelectedCardIndex(playerId : Int) : Int

    // update the index of the 2 drawn cards
    @Query("UPDATE " + PlayCards.TABLE_NAME
    + " SET cardIndex = :index WHERE id = :playerId")
    fun setSelectedCardInex(playerId : Int, index : Int)

    // Get player's turn status
    @Query("SELECT turn FROM " + PlayCards.TABLE_NAME
    + " WHERE id = :playerId")
    fun getPlayerTurnStatus(playerId: Int) : Boolean

    // Set player's turn status
    @Query("UPDATE " + PlayCards.TABLE_NAME
    + " SET turn = :status WHERE id = :playerId" )
    fun setPlayerTurnStatus(playerId: Int, status : Boolean)


}