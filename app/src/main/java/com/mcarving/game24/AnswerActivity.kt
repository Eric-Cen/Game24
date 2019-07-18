package com.mcarving.game24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_answer.*
import kotlinx.android.synthetic.main.activity_game.*

class AnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        setupPlayerName()

    }

    fun setupPlayerName(){

        val playerName = intent.getStringExtra(EXTRA_PLAYER_NAME)
        tv_player_name.text = "Player, $playerName's turn to anwer"

    }

    companion object {
        val EXTRA_PLAYER_NAME = "player's name"

    }
}
