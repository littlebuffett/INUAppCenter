package com.example.appcenter3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.appcenter3.fragment.BeforeFragment
import com.example.appcenter3.fragment.DoneFragment
import com.example.appcenter3.fragment.IngFragment

class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
       return when(position){
           0-> BeforeFragment()
           1-> IngFragment()
           2-> DoneFragment()
           else -> BeforeFragment()
       }
    }
}