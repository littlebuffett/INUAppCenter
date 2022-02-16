package com.example.appcenter3

import android.annotation.SuppressLint
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private lateinit var item:ItemData
    val tv_listText:TextView = view.findViewById(R.id.tv_listText)
    val iv_listImg:ImageView = view.findViewById(R.id.iv_listImg)

    fun bind(item:ItemData){
        this.item = item

        if(item.isLastItme)
            setLastItem()
        else
            setItem(this.item)
        Log.d("Item","${item.listText} binded")
    }

    private fun setLastItem(){
        tv_listText.text = ""
        tv_listText.setBackgroundResource(R.drawable.under_line)
        iv_listImg.setImageResource(R.drawable.ic_add)
    }

    private fun setItem(item:ItemData){
        //텍스트 세팅 길어지는 부분은 ...으로 표시. 텍스트 자르고 ... 붙이기.
        if(item.listText.length >= 21) tv_listText.text = shortenedStr(item.listText)
        else tv_listText.text = item.listText

        tv_listText.setBackgroundResource(0)

        //진행 전, 진행 중, 완료 상태에 따라 이미지 설정.
        if(item.state != 2)
            iv_listImg.setImageResource(R.drawable.rect)
        else //state가 2 -> 완료 탭에 표시될 이미지.
            iv_listImg.setImageResource(R.drawable.ic_delete)
    }

    private fun shortenedStr(str:String):String{
        return str.substring(0,20) + "..."
    }
}