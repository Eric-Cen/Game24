package com.mcarving.game24.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mcarving.game24.cards.Card

class ListConverter {
    @TypeConverter
    fun jsonToList(jsonText : String) : MutableList<Card>{
        val newList = mutableListOf<Card>()

        //TODO
        return newList
    }

    @TypeConverter
    fun listToJson(cards : MutableList<Card>) : String {
        val jText = Gson().toJson(cards)

        //TODO
        return jText
    }
}