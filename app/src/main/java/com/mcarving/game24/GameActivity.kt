package com.mcarving.game24

import android.graphics.Point
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

        val thisDisplay = this.windowManager.defaultDisplay
        val desiredSize = Point()
        thisDisplay.getSize(desiredSize)
        val width = desiredSize.x


        setupPlayerNames()
        generateFourRandomCards()

    }

    fun generateFourRandomCards(){
        // retrieve 4 cards from GameViewModel

        val card1 = Card.DECK[0]
        val card2 = Card.DECK[1]
        val card3 = Card.DECK[2]
        val card4 = Card.DECK[51]
        //val cardView1 = CardView(applicationContext)
//        val cardv = findViewById<CardView>(R.id.view1)
//        cardv.bind(card1)
        view1.bind(card1)
        view2.bind(card2)
        view3.bind(card3)
        view4.bind(card4)

    }

    fun setupPlayerNames(){
        val nameOne = intent.getStringExtra(EXTRA_PLAYER_ONE)
        val nameTwo = intent.getStringExtra(EXTRA_PLAYER_TWO)
        tv_player_one.text = nameOne
        tv_player_two.text = nameTwo
    }

    companion object {
        val EXTRA_PLAYER_ONE = "player1's name"
        val EXTRA_PLAYER_TWO = "player2's name"
    }
}