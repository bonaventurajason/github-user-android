package com.bonaventurajason.githubuser.helper

import android.content.Context
import java.io.IOException

object Utils {


    fun getJsonDataFromAsset(context: Context, fileName: String): String?{
        val jsonString: String
        try{
            jsonString = context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
        } catch (e : IOException){
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}