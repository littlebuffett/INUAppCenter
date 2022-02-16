package com.example.appcenter3.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

class DoneSharedPref(context: Context){

    val PREFS_FILENAME = "doneprefs"

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    fun getText(i:Int):String? = prefs.getString(i.toString() + LIST_TEXT, "")

    fun getState(i:Int):Int? = prefs.getInt(i.toString() + STATE,0)

    fun getLastItem(i:Int):Boolean? = prefs.getBoolean(i.toString() + LAST_ITEM, false)


    fun setText(i:Int, value:String) = prefs.edit().putString(i.toString() + LIST_TEXT, value).apply()

    fun setState(i:Int,value:Int) = prefs.edit().putInt(i.toString() + STATE, value).apply()

    fun setLastItem(i:Int,value:Boolean) = prefs.edit().putBoolean(i.toString() + LAST_ITEM, value).apply()


    fun clearAll() = prefs.edit().clear().apply()

    fun saved(i:Int) {
        prefs.edit().putBoolean("isfirst",false).apply()
        prefs.edit().putInt("size",i).apply()
    }

    fun getSize():Int = prefs.getInt("size",0)

    fun isFirst():Boolean = prefs.getBoolean("isfirst",true)

}