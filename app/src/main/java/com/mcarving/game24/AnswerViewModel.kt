package com.mcarving.game24

import androidx.lifecycle.ViewModel
import com.mcarving.game24.cards.Card

class AnswerViewModel : ViewModel(){
    lateinit var fourCards : List<Card>
    lateinit var player : Player
    lateinit var playerName : String
}