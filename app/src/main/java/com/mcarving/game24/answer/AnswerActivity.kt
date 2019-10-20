package com.mcarving.game24.answer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mcarving.game24.R
import com.mcarving.game24.cards.Card
import kotlinx.android.synthetic.main.activity_answer.*
import timber.log.Timber
import java.lang.StringBuilder
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
                val bValue = validateExpression(mathExpression)
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

            var strToAdd : String = convertStr(childStr)


            if(i > 0 ) {
                strToAdd = " ".plus(strToAdd)
            }

            strVal += strToAdd


        }

        return strVal

    }

    private fun convertStr(childStr : String) : String {
        var returnStr = ""
        if(childStr.isEmpty()){

        } else if(childStr[0] == '1' && childStr[1] == '0'){
            returnStr = "10"
        } else {
            returnStr = when (childStr[0]) {
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

        return returnStr
    }

    private fun evaluate(expressions : String) : String {

        val ops = Stack<String>() // operations
        val vals = Stack<Double>() // digits

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
            else {
                vals.push(if(temp.isNotEmpty()) temp.toDouble() else 0.0)
            }

            Timber.d("\nstr[$i] = " + parts[i])
        }

        val returnVal = vals.pop()

        return returnVal.toString()
    }

    private fun postfixToInfix(postfix : String) : String {

        class Expression {
            lateinit var op : String
            lateinit var ex : String

            val prec = 3

            Expression(e : String){
                ex = e
            }

            Epression(e1 : String, e2 : String, o : String){
                ex = String.format("%s %s %s", e1, o, e2)
                op = o
                prec = OPS.indexOf(o) / 2
            }
        }
    }
    private fun infixToPostfix(infix : CharArray) : CharArray {
        val sb = StringBuilder()
        val s = Stack<Int>()

        try {
            for( c in infix){
                val idx = OPS.indexOf(c)
                if(idx != -1){
                    if(s.isEmpty()){
                        s.push(idx)
                    } else {
                        while(!s.isEmpty()){
                            val prec2 = s.peek() / 2
                            val prec1 = idx / 2
                            if (prec2 >= prec1)
                                sb.append(OPS[s.pop()])
                            else
                                break
                        }
                        s.push(idx)
                    }
                } else if ( c == '(') {
                    s.push(-2)
                } else if ( c == ')') {
                    while (s.peek() != -2 )
                        sb.append(OPS[s.pop()])
                    s.pop()
                } else {
                    sb.append(c)
                }
            }

            while(!s.isEmpty()) {
                sb.append(OPS[s.pop()])
            }

        } catch (e : EmptyStackException){
            throw Exception("Invalid entry.")
        }

        return sb.toString().toCharArray()

    }
    
    private fun validateExpression(inputString : String) : Boolean {
        val parensExpr = inputString.replace("[^()]".toRegex(), "")

        val arr = parensExpr.toCharArray()

        val parensStack = Stack<Char>()
        for(i in 0 until arr.size){
            try{
                if(arr[i] == '('){
                    parensStack.push(arr[i])
                } else {
                    parensStack.pop()
                }
            } catch(e : EmptyStackException){
                parensStack.push(arr[i])
            }
        }

        val opsExpr = inputString.replace("[()\\s\\d]".toRegex(), "")
        Timber.d("opsExpr = $opsExpr")
        val opsArray = opsExpr.toCharArray()
        Timber.d("operation counts = ${opsArray.size}")

        val digitExpr = inputString.replace("[^\\d\\s]".toRegex(), "").trim()
        Timber.d("digitExpr = $digitExpr")

        val numbers = digitExpr.split("\\s+".toRegex())
        Timber.d("number count = ${numbers.size}")
        Timber.d("numbers => ${numbers.toString()}")

        return parensStack.isEmpty() && opsArray.size == 3 && numbers.size == 4
    }


    companion object {
        val EXTRA_PLAYER_NAME = "player's name"
        val EXTRA_FOUR_CARDS = "four cards"
        val OPS = "+−×÷"

    }
}
