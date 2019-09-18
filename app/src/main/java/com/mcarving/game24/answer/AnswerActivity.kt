package com.mcarving.game24.answer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mcarving.game24.R
import kotlinx.android.synthetic.main.activity_answer.*

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
