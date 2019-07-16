package com.mcarving.game24


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mcarving.game24.GameActivity.Companion.EXTRA_PLAYER_ONE
import com.mcarving.game24.GameActivity.Companion.EXTRA_PLAYER_TWO
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    //Game Activity

    // randomize 4 cards in an array (combine two cards from the two players

    private val TAG = "MainActivity"

    private lateinit var editTextOne : EditTextWithClear
    private lateinit var editTextTWo : EditTextWithClear

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextOne = findViewById(R.id.edit_name1)
        editTextTWo = findViewById(R.id.edit_name2)
        
        btn_play.setOnClickListener {
            val (playerOneName, playerTwoName) = getPlayerNames()

            // start GameActivity
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra(EXTRA_PLAYER_ONE, playerOneName)
                putExtra(EXTRA_PLAYER_TWO, playerTwoName)
            }

            startActivity(intent)
            
        }
    }

    private fun getPlayerNames() : Pair<String, String>{
        val  nameOne = if(editTextOne.text.isNullOrBlank()) "Player 1"
                                else editTextOne.text.toString().trim()
        val nameTwo = if(editTextTWo.text.isNullOrBlank()) "Player 2"
                                else editTextTWo.text.toString().trim()

        return Pair(nameOne, nameTwo)
    }
}
