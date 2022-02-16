package com.example.appcenter3

import androidx.lifecycle.ViewModel

class AllList : ViewModel() {

    var BeforeItems = mutableListOf<ItemData>(
        //ItemData("살 빼기",0,false),
        //ItemData("앱센터 과제하기",0,false)
 )

    var IngItems = mutableListOf<ItemData>(

        )

    var DoneItems = mutableListOf<ItemData>(

       )

    fun addToIng(item:ItemData){
        item.state = 1
        IngItems.add(item)
    }

    fun addToDone(item:ItemData){
        item.state = 2
        DoneItems.add(item)
    }

}