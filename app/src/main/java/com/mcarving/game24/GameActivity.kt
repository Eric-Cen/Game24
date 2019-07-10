package com.mcarving.game24

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.mcarving.game24.cards.Card
import com.mcarving.game24.cards.CardView
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // remove app title bar
        supportActionBar?.apply { hide() }

    }

    fun generateFourRandomCards(){
        // retrieve 4 cards from GameViewModel

        val card1 = Card.DECK[0]
        val card2 = Card.DECK[1]
        val card3 = Card.DECK[2]
        val card4 = Card.DECK[51]
        val cardView1 = CardView(this.layoutInflater, view1)
        cardView1.bind(card1)

    }

    companion object {
        val EXTRA_PLAYER_ONE = "player1's name"
        val EXTRA_PLAYER_TWO = "player2's name"
    }
}