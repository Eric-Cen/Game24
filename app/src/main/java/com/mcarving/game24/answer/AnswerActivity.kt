package com.mcarving.game24.answer

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mcarving.game24.R
import com.mcarving.game24.cards.Card
import kotlinx.android.synthetic.main.activity_answer.*

class AnswerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        // remove app title bar
        supportActionBar?.apply { hide() }

        setup()

    }

    fun setup(){

        val playerName = intent.getStringExtra(EXTRA_PLAYER_NAME)
        //tv_player_name.text = "Player, $playerName's turn to answer"
        tv_answer.text = "$playerName's answer = ?"
        val fourCards = intent.getParcelableArrayListExtra<Card>(EXTRA_FOUR_CARDS)
        chip_card1.text = fourCards[0].toString()
        chip_card1.tag =  fourCards[0].toString()

        chip_card2.text = fourCards[1].toString()
        chip_card2.tag =  fourCards[1].toString()

        chip_card3.text = fourCards[2].toString()
        chip_card3.tag =  fourCards[2].toString()

        chip_card4.text = fourCards[3].toString()
        chip_card4.tag =  fourCards[3].toString()


        chip_card1.setOnClickListener { card1 : View ->
            card1.isEnabled = false

            val chip = Chip(this)
            chip.text = card1.tag as String
            chip.tag = card1.tag
            chip.setOnClickListener {
                card1.isEnabled = true
                chipGroup1.removeView(chip)
            }
            chipGroup1.addView(chip)
        }
        
        chip_card2.setOnClickListener { card2 : View ->
            card2.isEnabled = false

            val chip2 = Chip(this)
            chip2.text = card2.tag as String
            chip2.tag = card2.tag
            chip2.setOnClickListener {
                card2.isEnabled = true
                chipGroup1.removeView(chip2)
            }
            chipGroup1.addView(chip2)
        }

        chip_card3.setOnClickListener{card3 : View ->
            card3.isEnabled = false

            val chip3 = Chip(this)
            chip3.text = card3.tag as String
            chip3.tag = card3.tag
            chip3.setOnClickListener {
                card3.isEnabled = true
                chipGroup1.removeView(chip3)
            }
            chipGroup1.addView(chip3)
        }

        chip_card4.setOnClickListener{card4 : View ->
            card4.isEnabled = false

            val chip4 = Chip(this)
            chip4.text = card4.tag as String
            chip4.tag = card4.tag
            chip4.setOnClickListener {
                card4.isEnabled = true
                chipGroup1.removeView(chip4)
            }
            chipGroup1.addView(chip4)
        }

        chip_plus.setOnClickListener {
            val plusChip = copyChip(this, it as Chip, chipGroup1, false)

            chipGroup1.addView(plusChip)
        }

        chip_minus.setOnClickListener {
            val minusChip = copyChip(this, it as Chip, chipGroup1, false)

            chipGroup1.addView(minusChip)
        }

        chip_multiply.setOnClickListener {
            val multiplyChip = copyChip(this, it as Chip, chipGroup1, false)

            chipGroup1.addView(multiplyChip)
        }

        chip_divide.setOnClickListener {
            val divideChip = copyChip(this, it as Chip, chipGroup1, false)

            chipGroup1.addView(divideChip)

        }

        chip_left_parenthesis.setOnClickListener {
            val leftParenthesisChip = copyChip(this, it as Chip, chipGroup1, false)

            chipGroup1.addView(leftParenthesisChip)

        }

        chip_right_parenthese.setOnClickListener {
            val rightParenthesisChip = copyChip(this, it as Chip, chipGroup1, false)

            chipGroup1.addView(rightParenthesisChip)
        }

    }

    fun copyChip(context: Context,
                           selectedChip : Chip,
                           chipGroup: ChipGroup,
                           isEnabled : Boolean) : Chip{
        val chip4 = Chip(this)
        chip4.text = selectedChip.tag as String
        chip4.tag = selectedChip.tag
        chip4.setOnClickListener {
            if(isEnabled) {
                selectedChip.isEnabled = true
            }
            chipGroup.removeView(chip4)
        }
        return chip4
    }



    companion object {
        val EXTRA_PLAYER_NAME = "player's name"
        val EXTRA_FOUR_CARDS = "four cards"

    }
}
