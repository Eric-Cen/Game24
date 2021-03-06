package com.mcarving.game24.data.source.local

import androidx.room.*
import com.mcarving.game24.cards.Card
import com.mcarving.game24.data.PlayCards

@Dao
interface PlayCardsDao {
    // Get player's cards in hand
    @TypeConverters(ListConverter::class)
    @Query("SELECT * from " + PlayCards.TABLE_NAME
    + " WHERE id = :playerId")
    fun getPlayCards(playerId: Int) : PlayCards?

    // Set player's cards in hand
    @TypeConverters(ListConverter::class)
    @Query("UPDATE " + PlayCards.TABLE_NAME
    + " SET cardsInHand = :cards WHERE  id = :playerId")
    fun setCardsInHand(playerId: Int, cards : List<Card>)

    // Get player's 2 drawn cards
//    @TypeConverters(ListConverter::class)
//    @Query("SELECT twoCards from " + PlayCards.TABLE_NAME
//    + " WHERE id = :playerId")
//    fun getTwoCards(playerId: Int) : List<Card>

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


    /**
     *  Select all PlayCards from the table
     */
    @Query("SELECT * FROM " + PlayCards.TABLE_NAME)
    fun getAllPlayCards() : List<PlayCards>

    /**
     * Insert a playCards into the table.  If the playCard already exists, replace it
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(playCards: PlayCards)

    /**
     * Update a playCards
     */
    @Update
    fun update(playCards: PlayCards) : Int

    /**
     * Delete a playCards entry by id
     * @return the number of row deleted.  This should always be 1
     */
    @Query("DELETE FROM " + PlayCards.TABLE_NAME + " where id = :playerId")
    fun deletePlayCardsById(playerId: Int) : Int


    /**
     * delete all content for the cards table
     */
    @Query("DELETE FROM " + PlayCards.TABLE_NAME)
    fun deleteAll()

}