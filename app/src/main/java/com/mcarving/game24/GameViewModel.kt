package com.mcarving.game24

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mcarving.game24.cards.Card
import kotlin.random.Random

class GameViewModel : ViewModel() {
    enum class Player {
        ONE, TWO
    }

    val player_one_pass = MutableLiveData<Boolean>()
    var player_two_pass = MutableLiveData<Boolean>()

    // list of poker cards for player 1
    private lateinit var playerOneCards : MutableList<Card>
    private var currentPointerOne = -1

    // list of poker cards for player 2
    private lateinit var playerTwoCards : MutableList<Card>
    private var currentPointerTwo = -1

    init {
        player_one_pass.value = false
        player_two_pass.value = false

        Log.d("CardViewModel", "shuffleDeck: ${Card.DECK.toString()}")
        // initial shuffle cards
        val cardDeck = shuffleCards(Card.DECK)

        val(a, b) = cardDeck.partition {acard ->
            cardDeck.indexOf(acard) % 2 == 0
        }

        Log.d("CardViewModel", "shuffleDeck: ${a.toString()}")
        Log.d("CardViewModel", "shuffleDeck: ${a.size}")

        Log.d("CardViewModel", "shuffleDeck: ${b.toString()}")
        Log.d("CardViewModel", "shuffleDeck: ${b.size}")

        playerOneCards = a.toMutableList()
        playerTwoCards = b.toMutableList()

        reshuffleCardsInHand()
    }

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


        Log.d("CardViewModel", "shuffleDeck before: ${cards.toString()}")
        Log.d("CardViewModel", "shuffleDeck: after: ${shuffledCards.toString()}")

        return shuffledCards
    }

    fun reshuffleCardsInHand(){
        if(playerOneCards.size > 1){
            playerOneCards = shuffleCards(playerOneCards)
            Log.d("CardViewModel", "reshuffled player one: ${playerOneCards.toString()}")

        }

        if(playerTwoCards.size > 1){

            playerTwoCards = shuffleCards(playerTwoCards)
            Log.d("CardViewModel", "reshuffled player Two: ${playerTwoCards.toString()}")


        }


    }

    fun updateResult(cardsChanged : List<Card>, winner : Player, loser : Player){
        addCards(cardsChanged, loser)
        removeCards(cardsChanged, winner)
    }

    // if the user loses, the user collects the two cards from other player
    fun addCards(addList : List<Card>, who : Player){
        for(card in addList){
            when(who){
                Player.ONE -> playerOneCards.add(card)
                Player.TWO -> playerTwoCards.add(card)
            }
        }
    }

    // if the user wins, the user gives aways the two cards
    fun removeCards(removeList : List<Card>, who : Player ){
        for (card in removeList){
            when(who){
                Player.ONE -> playerOneCards.remove(card)
                Player.TWO -> playerTwoCards.remove(card)
            }
        }

    }

    fun setupPassByPlayer(player : Player, status : Boolean){
        when (player){
            Player.ONE -> player_one_pass.value = status
            Player.TWO -> player_two_pass.value = status
        }
    }

    fun getPassByPlayer(player: Player) : Boolean?{
        when(player){
            Player.ONE -> return player_one_pass.value
            Player.TWO -> return player_two_pass.value
        }
    }

    fun getCardSize(player: Player) : Int {
        when(player){
            Player.ONE -> return playerOneCards.size
            Player.TWO -> return playerTwoCards.size
        }
    }

    fun getFourCards() : List<Card>{

        if(currentPointerOne == (playerOneCards.size-1)){
            playerOneCards = shuffleCards(playerOneCards)
            currentPointerOne = -1
        }

        if(currentPointerTwo == (playerTwoCards.size-1)){
            playerTwoCards = shuffleCards(playerTwoCards)
            currentPointerTwo = -1

        }
        val tempList = MutableList<Card>(4){Card.DECK[0]}
        if(playerOneCards.size >= 2 && playerTwoCards.size >= 2){
            tempList[0] = playerOneCards[++currentPointerOne]
            tempList[1] = playerOneCards[++currentPointerOne]

            tempList[2] = playerTwoCards[++currentPointerTwo]
            tempList[3] = playerTwoCards[++currentPointerTwo]
        } else if(playerOneCards.size < 2 && playerTwoCards.size >= 2){

        } else if(playerOneCards.size >= 2 && playerTwoCards.size < 2){

        }

        return tempList
    }

}