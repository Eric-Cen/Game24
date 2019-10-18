package com.mcarving.game24.answer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.ChipGroup
import com.mcarving.game24.Player
import com.mcarving.game24.cards.Card

class AnswerViewModel : ViewModel(){
    lateinit var fourCards : List<Card>
    lateinit var player : Player
    lateinit var playerName : String

    val chipCount = MutableLiveData<Int>()

    init{
        chipCount.value = 0
    }
}