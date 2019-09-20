package com.mcarving.game24.data.source.local

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mcarving.game24.cards.Card

class ListConverter {
    val TAG = "ListConverter"
    
    @TypeConverter
    fun jsonToList(jsonText : String) : MutableList<Card>{


        val newList = mutableListOf<Card>()
        val stringList = Gson().fromJson(jsonText, Array<String>::class.java).asList()

        val delimiter = " "
        stringList.forEach {
            val parts = it.split(delimiter)
            if(parts.size == 2) {
                val card = Card(parts[1], parts[0])
                newList.add(card)
            }
        }

        return newList
    }

    @TypeConverter
    fun listToJson(cards : MutableList<Card>) : String {

        val stringList = arrayListOf<String>()
        cards.forEach {
            stringList.add(it.toString())
        }

        val jText = Gson().toJson(stringList)

        Log.d(TAG, "listToJson: $jText")
        return jText
    }
}