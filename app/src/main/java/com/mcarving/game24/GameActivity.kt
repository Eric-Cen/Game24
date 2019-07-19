package com.mcarving.game24

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mcarving.game24.cards.Card
import com.mcarving.game24.cards.CardView
import kotlinx.android.synthetic.main.activity_game.*
import java.lang.Exception


class GameActivity : AppCompatActivity() {
    private lateinit var nameOne: String

    private lateinit var nameTwo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // remove app title bar
        supportActionBar?.apply { hide() }

        setupPlayerNames()

        val viewModel = ViewModelProviders.of(this)
            .get(GameViewModel::class.java)

        displayFourCards(viewModel.getFourCards())

        btn_answer1.setOnClickListener {
            startAnswerActivity(nameOne)
        }

        btn_answer2.setOnClickListener {

            startAnswerActivity(nameTwo)

        }

        btn_pass1.setOnClickListener {
            viewModel.setupPassByPlayer(GameViewModel.Player.ONE, true)
            it.isEnabled = false
        }

        val playerOneObserver = Observer<Boolean> { player_one_passes ->
            // check if player 2 passes, if yes, display another 4 cards
            if (viewModel.getPassByPlayer(GameViewModel.Player.TWO) == true) {
                setPassButtonText("2/2 Passes")

                displayFourCards(viewModel.getFourCards())

                clearPassText()
                viewModel.setupPassByPlayer(GameViewModel.Player.ONE, false)
                viewModel.setupPassByPlayer(GameViewModel.Player.TWO, false)
            } else if (viewModel.getPassByPlayer(GameViewModel.Player.ONE) == false) {
                setPassButtonText("0/2 Pass")

            } else {
                setPassButtonText("1/2 Pass")

            }
        }

        viewModel.player_one_pass.observe(this, playerOneObserver)

        btn_pass2.setOnClickListener {
            viewModel.setupPassByPlayer(GameViewModel.Player.TWO, true)
            it.isEnabled = false
        }


        val playerTwoObserver = Observer<Boolean> { player_two_passes ->
            // check if player 2 passes, if yes, display another 4 cards
            if (viewModel.getPassByPlayer(GameViewModel.Player.ONE) == true) {
                setPassButtonText("2/2 Passes")

                displayFourCards(viewModel.getFourCards())
                clearPassText()



                viewModel.setupPassByPlayer(GameViewModel.Player.ONE, false)
                viewModel.setupPassByPlayer(GameViewModel.Player.TWO, false)
            } else if (viewModel.getPassByPlayer(GameViewModel.Player.TWO) == false) {

                setPassButtonText("0/2 Pass")

            } else {
                setPassButtonText("1/2 Pass")
            }
        }

        viewModel.player_two_pass.observe(this, playerTwoObserver)
    }

    fun setPassButtonText(msg: String) {
        btn_pass1.text = msg
        btn_pass2.text = msg

    }

    fun clearPassText(){
        btn_pass1.text = "0/2 Pass"
        btn_pass2.text = "0/2 Pass"

        btn_pass1.isEnabled = true
        btn_pass2.isEnabled = true


    }

    fun startAnswerActivity(playerName: String) {

        val intent = Intent(this, AnswerActivity::class.java).apply {
            putExtra(AnswerActivity.EXTRA_PLAYER_NAME, playerName)
        }

        startActivity(intent)

    }


    fun displayFourCards(cards : List<Card>){
        if(cards.size == 4){
            view1.bind(cards[0])
            view2.bind(cards[1])
            view3.bind(cards[2])
            view4.bind(cards[3])
        } else {
            throw Exception("Invalid card size")
        }

    }

    fun setupPlayerNames() {
        nameOne = intent.getStringExtra(EXTRA_PLAYER_ONE)
        nameTwo = intent.getStringExtra(EXTRA_PLAYER_TWO)
        tv_player_one.text = nameOne
        tv_player_two.text = nameTwo
    }

    companion object {
        val EXTRA_PLAYER_ONE = "player1's name"
        val EXTRA_PLAYER_TWO = "player2's name"
    }
}