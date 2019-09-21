package com.mcarving.game24.cards

import android.os.Bundle

/**
 *Poker card class
 */

class Card constructor(val suit: String, val value: String){
    val cornerLabel: String
        get() = value + "\n" + suit

    fun toBundle(): Bundle {
        val args = Bundle(1)
        args.putStringArray(ARGS_BUNDLE, arrayOf(suit, value))
        return args
    }

    override fun toString(): String {
        return "$value $suit"
    }

    companion object {
        internal val ARGS_BUNDLE = Card::class.java.name + ":Bundle"

        val SUITS = setOf("♣" /* clubs*/, "♦" /* diamonds*/, "♥" /* hearts*/, "♠" /*spades*/)
        val VALUES = setOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A")
        val DECK = SUITS.flatMap { suit ->
            VALUES.map { value -> Card(suit, value) }
        }

        /** Use in conjunction with [Card.toBundle] */
        fun fromBundle(bundle: Bundle): Card{
            val spec = bundle.getStringArray(ARGS_BUNDLE)
            return Card(spec!![0], spec[1])
        }
    }
}