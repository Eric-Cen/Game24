package com.mcarving.game24

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mcarving.game24.cards.Card
import kotlinx.android.synthetic.main.activity_game.*
import java.lang.Exception


class GameActivity : AppCompatActivity() {
    private lateinit var _nameOne: String

    private lateinit var _nameTwo: String

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
            startAnswerActivity(_nameOne)
        }

        btn_answer2.setOnClickListener {
            startAnswerActivity(_nameTwo)
        }

        btn_pass1.setOnClickListener {
            viewModel.setupPassByPlayer(GameViewModel.Player.ONE, true)
        }

        val playerOneObserver = Observer<Boolean> { player_one_passes ->
            btn_pass1.isEnabled = !player_one_passes

            val bb = !player_one_passes
            Log.d("GameActivity", "onCreate: btn_pass1.isEnable = $bb")

            // check if player 2 passes, if yes, display another 4 cards
            updatePassButtonsStage(viewModel)
        }

        viewModel.player_one_pass.observe(this, playerOneObserver)

        btn_pass2.setOnClickListener {
            viewModel.setupPassByPlayer(GameViewModel.Player.TWO, true)
        }


        val playerTwoObserver = Observer<Boolean> { player_two_passes ->
            btn_pass2.isEnabled = !player_two_passes
            val aa = !player_two_passes
            Log.d("GameActivity", "onCreate: btn_pass2.isEnable = $aa")

            // check if player 2 passes, if yes, display another 4 cards
            updatePassButtonsStage(viewModel)
        }

        viewModel.player_two_pass.observe(this, playerTwoObserver)
        subscribeTimer(viewModel)
    }

    fun updatePassButtonsStage(viewModel: GameViewModel) {
        if (viewModel.getPassByPlayer(GameViewModel.Player.ONE) == true
            && viewModel.getPassByPlayer(GameViewModel.Player.TWO) == true
        ) {
            //setPassButtonText("2/2 Passes")

            displayFourCards(viewModel.getFourCards())

            setPassButtonText("0/2 Pass")
            hideTimerButtonText()
            viewModel.setupPassByPlayer(GameViewModel.Player.ONE, false)
            viewModel.setupPassByPlayer(GameViewModel.Player.TWO, false)
        } else if (viewModel.getPassByPlayer(GameViewModel.Player.TWO) == true
            || viewModel.getPassByPlayer(GameViewModel.Player.ONE) == true
        ) {
            setPassButtonText("1/2 Pass")
            viewModel.countDown30secs()

            if (viewModel.getPassByPlayer(GameViewModel.Player.ONE) == true){
                // setup timer for player two
                tv_player2_timer?.visibility = View.VISIBLE

            } else {
                // setup timer for player one
                tv_player1_timer?.visibility = View.VISIBLE
            }

        } else {
            setPassButtonText("0/2 Pass")

        }
    }

    fun setPassButtonText(msg: String) {
        btn_pass1.text = msg
        btn_pass2.text = msg

    }

    fun hideTimerButtonText(){
        tv_player2_timer?.visibility = View.GONE
        tv_player1_timer?.visibility = View.GONE
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
        _nameOne = intent.getStringExtra(EXTRA_PLAYER_ONE)
        _nameTwo = intent.getStringExtra(EXTRA_PLAYER_TWO)
        tv_player_one.text = _nameOne
        tv_player_two.text = _nameTwo
    }

    fun subscribeTimer(viewModel: GameViewModel){
        val elapsedTimeObserver = Observer<Long> { aLong ->

            val resId = when {
                aLong > 1 -> R.string.seconds
                aLong == 1L -> R.string.second
                else -> R.string.time_out
            }

            val newText = this.resources.getString(resId, aLong)

            if (viewModel.player_one_pass.value == true && viewModel.player_two_pass.value == false){
                // show timer for Player 2
                tv_player2_timer?.text = newText
            } else if (viewModel.player_two_pass.value == true && viewModel.player_one_pass.value == false) {
                // show timer for Player 1
                tv_player1_timer?.text = newText
            } else {
                // cancel timer and hide timer msg
                viewModel.cancelTimer()
            }
        }

        viewModel.getElapsedTime().observe(this, elapsedTimeObserver)
    }

    companion object {
        val EXTRA_PLAYER_ONE = "player1's name"
        val EXTRA_PLAYER_TWO = "player2's name"
    }
}