package com.mcarving.game24

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mcarving.game24.cards.Card
import java.util.*
import kotlin.random.Random

class GameViewModel : ViewModel() {

    val TAG = "GameViewModel"

    val player_one_pass = MutableLiveData<Boolean>()
    var player_two_pass = MutableLiveData<Boolean>()

    // list of poker cards for player 1
    private lateinit var _playerOneCards : MutableList<Card>
    private var _currentPointerOne = -1

    // list of poker cards for player 2
    private lateinit var _playerTwoCards : MutableList<Card>
    private var _currentPointerTwo = -1

    private var _elapsedTime = MutableLiveData<Long>()

    private lateinit var _timer : Timer

    init {
        player_one_pass.value = false
        player_two_pass.value = false

        //_elapsedTime.value = THIRTY_SECONDS

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

        _playerOneCards = a.toMutableList()
        _playerTwoCards = b.toMutableList()

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
        if(_playerOneCards.size > 1){
            _playerOneCards = shuffleCards(_playerOneCards)
            Log.d("CardViewModel", "reshuffled player one: ${_playerOneCards.toString()}")

        }

        if(_playerTwoCards.size > 1){

            _playerTwoCards = shuffleCards(_playerTwoCards)
            Log.d("CardViewModel", "reshuffled player Two: ${_playerTwoCards.toString()}")


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
                Player.ONE -> _playerOneCards.add(card)
                Player.TWO -> _playerTwoCards.add(card)
            }
        }
    }

    // if the user wins, the user gives aways the two cards
    fun removeCards(removeList : List<Card>, who : Player ){
        for (card in removeList){
            when(who){
                Player.ONE -> _playerOneCards.remove(card)
                Player.TWO -> _playerTwoCards.remove(card)
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
            Player.ONE -> return _playerOneCards.size
            Player.TWO -> return _playerTwoCards.size
        }
    }

    fun getFourCards() : List<Card>{

        if(_currentPointerOne == (_playerOneCards.size-1)){
            _playerOneCards = shuffleCards(_playerOneCards)
            _currentPointerOne = -1
        }

        if(_currentPointerTwo == (_playerTwoCards.size-1)){
            _playerTwoCards = shuffleCards(_playerTwoCards)
            _currentPointerTwo = -1

        }
        val tempList = MutableList<Card>(4){Card.DECK[0]}
        if(_playerOneCards.size >= 2 && _playerTwoCards.size >= 2){
            tempList[0] = _playerOneCards[++_currentPointerOne]
            tempList[1] = _playerOneCards[++_currentPointerOne]

            tempList[2] = _playerTwoCards[++_currentPointerTwo]
            tempList[3] = _playerTwoCards[++_currentPointerTwo]
        } else if(_playerOneCards.size < 2 && _playerTwoCards.size >= 2){

        } else if(_playerOneCards.size >= 2 && _playerTwoCards.size < 2){

        }

        return tempList
    }

    fun countDown30secs(){
        var initialtime : Long = SystemClock.elapsedRealtime()

        // should this run with coroutine?
        _timer = Timer()
        _timer.scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - initialtime) / 1000
                _elapsedTime.postValue(THIRTY_SECONDS - newValue)

                Log.d(TAG, "run: newValue = $newValue")
                if(newValue >= 30){
                    Log.d(TAG, "run: timer reach 30 seconds, it is cancelled")
                    cancel()
                }
            }
        }, ONE_SECOND, ONE_SECOND)
    }

    fun cancelTimer(){
        _timer.cancel()
    }

    fun getElapsedTime() : LiveData<Long>{
        return _elapsedTime
    }

    companion object {
        val ONE_SECOND = 1000L
        val THIRTY_SECONDS = 30L
    }

}