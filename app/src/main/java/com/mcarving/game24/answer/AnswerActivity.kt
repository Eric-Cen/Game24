package com.mcarving.game24.answer

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mcarving.game24.R
import com.mcarving.game24.cards.Card
import kotlinx.android.synthetic.main.activity_answer.*
import kotlinx.android.synthetic.main.activity_answer.view.*
import timber.log.Timber
import java.util.*

class AnswerActivity : AppCompatActivity() {

    private lateinit var _viewModel : AnswerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        // remove app title bar
        supportActionBar?.apply { hide() }

        setup()

        _viewModel = ViewModelProvider.NewInstanceFactory().create(AnswerViewModel::class.java)

        _viewModel.chipCount.value = chipGroup1.childCount

        val countObserver = Observer<Int>{
            Timber.d("chip size = $it")
            val mathExpression = getMathmaticalExpression(chipGroup1)
            if(mathExpression.isNotEmpty()) {
                tv_show_calc.text = mathExpression


                // if yes, should math result

                //determine if math expression is valid
                val bValue = isProperExpression(mathExpression)
                Timber.d("proper format = $bValue")

                if (bValue) {
                    tv_answer.text = evaluate(mathExpression)
                }

            }

        }

        _viewModel.chipCount.observe(this, countObserver)
    }

    fun setup(){
        val playerName = intent.getStringExtra(EXTRA_PLAYER_NAME)
        //tv_player_name.text = "Player, $playerName's turn to answer"
        tv_player_answer.text = "$playerName's answer = ?"

        val fourCards = intent.getParcelableArrayListExtra<Card>(EXTRA_FOUR_CARDS)
        chip_card1.text = fourCards[0].toString()
        chip_card1.tag =  fourCards[0].toString()

        chip_card2.text = fourCards[1].toString()
        chip_card2.tag =  fourCards[1].toString()

        chip_card3.text = fourCards[2].toString()
        chip_card3.tag =  fourCards[2].toString()

        chip_card4.text = fourCards[3].toString()
        chip_card4.tag =  fourCards[3].toString()

        chip_card1.setOnClickListener(
            createOnClickListener(chip_card1, chipGroup1, true))

        chip_card2.setOnClickListener(
            createOnClickListener(chip_card2, chipGroup1, true))

        chip_card3.setOnClickListener(
            createOnClickListener(chip_card3, chipGroup1, true))

        chip_card4.setOnClickListener(
            createOnClickListener(chip_card4, chipGroup1, true))

        chip_plus.setOnClickListener(
            createOnClickListener(chip_plus, chipGroup1, false))

        chip_minus.setOnClickListener(
            createOnClickListener(chip_minus, chipGroup1, false))

        chip_multiply.setOnClickListener(
            createOnClickListener(chip_multiply, chipGroup1, false))

        chip_divide.setOnClickListener(
            createOnClickListener(chip_divide, chipGroup1, false))

        chip_left_parenthesis.setOnClickListener (
            createOnClickListener(chip_left_parenthesis, chipGroup1, false))

        chip_right_parenthese.setOnClickListener(
            createOnClickListener(chip_right_parenthese, chipGroup1, false))
    }

    private fun createOnClickListener(selectedChip : Chip,
                              chipGroup: ChipGroup,
                              isCard : Boolean) : View.OnClickListener {

        return View.OnClickListener{
            if(isCard){
                selectedChip.isEnabled = false
            }

            val copiedChip = copyChip(selectedChip, chipGroup, isCard)
            chipGroup.addView(copiedChip)
            _viewModel.chipCount.value = chipGroup.childCount
        }
    }

    private fun copyChip(selectedChip : Chip,
                           chipGroup: ChipGroup,
                           isCard : Boolean) : Chip{
        val chip4 = Chip(this)
        chip4.text = selectedChip.tag as String
        chip4.tag = selectedChip.tag
        chip4.setOnClickListener {
            if(isCard) {
                selectedChip.isEnabled = true
                selectedChip.text = selectedChip.tag as String
            }
            chipGroup.removeView(chip4)
            _viewModel.chipCount.value = chipGroup.childCount
        }

        return chip4
    }

    private fun getMathmaticalExpression(chipGroup : ChipGroup) : String {
        if(chipGroup.childCount == 0){
            return ""
        }

        var strVal = ""
        for(i in 0 until chipGroup.childCount){
            val childStr = chipGroup.getChildAt(i).tag as String

            var strToAdd : String

            if(childStr[0] == '1' && childStr[1] == '0'){
                strToAdd = "10"
            } else {
                strToAdd = when (childStr[0]) {
                    'K', 'Q', 'J' -> "10"
                    '9', '8', '7', '6', '5', '4', '3', '2' -> childStr[0].toString()
                    'A' -> "1"
                    '(' -> "("
                    ')' -> ")"
                    '+' -> "+"
                    '−' -> "−"
                    '×'  -> "×"
                    '÷'  -> "÷"
                    else -> ""
                }
            }
            strVal += strToAdd

            strVal += " "
        }

        return strVal

    }

    private fun evaluate(expressions : String) : String {
        var finalStr : String = "Player's answer = ??"

        var ops = Stack<String>() // operations
        var vals = Stack<Double>() // digits

        // remove all white spaces from expressions
//        val expr = expressions.replace("\\s".toRegex(), "")

        val delimiter = " "
        val parts = expressions.split(delimiter)
        for (i in 0 until parts.size){
            val temp = parts[i]
            if(temp.equals("(")) {}// do nothing
            else if(temp.equals("+"))  ops.push(temp)
            else if(temp.equals("−"))  ops.push(temp)
            else if(temp.equals("×"))  ops.push(temp)
            else if(temp.equals("÷"))  ops.push(temp)
            else if(temp.equals(")")){
                val op = ops.pop()
                var v = vals.pop()

                if(op.equals("+"))  v = vals.pop() + v
                else if(op.equals("−")) v = vals.pop() - v
                else if(op.equals("×")) v = vals.pop() * v
                else if(op.equals("÷")) v = vals.pop() / v

                vals.push(v)
            }
            else vals.push(temp.toDouble() ?: 0.toString)

            Timber.d("\nstr[$i] = " + parts[i])
        }

        val returnVal = vals.pop()
        finalStr = returnVal.toString()

        return finalStr
    }
    
    private fun isProperExpression(inputString : String) : Boolean {
        val expr = inputString.replace("[^()]".toRegex(), "")

        val arr = expr.toCharArray()

        val stack = Stack<Char>()
        for(i in 0 until arr.size){
            try{
                if(arr[i] == '('){
                    stack.push(arr[i])
                } else {
                    stack.pop()
                }
            } catch(e : EmptyStackException){
                stack.push(arr[i])
            }
        }

        return stack.isEmpty()
    }


    companion object {
        val EXTRA_PLAYER_NAME = "player's name"
        val EXTRA_FOUR_CARDS = "four cards"

    }
}
