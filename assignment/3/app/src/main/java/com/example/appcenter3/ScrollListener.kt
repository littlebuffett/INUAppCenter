package com.example.appcenter3

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout

class ScrollListener(val viewTab : TabLayout) : RecyclerView.OnScrollListener(){

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        when(newState){
            0 -> viewTab.elevation = 0f
            1 -> viewTab.elevation = 10f
            2 -> viewTab.elevation = 15f
        }
    }
}