package com.mcarving.game24


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mcarving.game24.GameActivity.Companion.EXTRA_PLAYER_ONE
import com.mcarving.game24.GameActivity.Companion.EXTRA_PLAYER_TWO
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //TODO  look into compound view to display 4 cards

    //Game Activity

    // randomize 4 cards in an array (combine two cards from the two players

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        btn_play.setOnClickListener {
            val (playerOneName, playerTwoName) = getPlayerNames()
            Log.d(TAG, "onCreate: playerOne = $playerOneName \nplayerTwo = $playerTwoName")

            // start GameActivity
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra(EXTRA_PLAYER_ONE, playerOneName)
                putExtra(EXTRA_PLAYER_TWO, playerTwoName)
            }

            startActivity(intent)
            
        }
    }

    private fun getPlayerNames() : Pair<String, String>{

        val  nameOne = if(edit_name1.text.isNullOrBlank()) "Player 1" else edit_name1.text.toString().trim()
        val nameTwo = if(edit_name2.text.isNullOrBlank()) "Player 2" else edit_name2.text.toString().trim()

        return Pair(nameOne, nameTwo)
    }
}
