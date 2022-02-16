package com.example.appcenter3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import androidx.core.view.get
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.appcenter3.fragment.BeforeFragment
import com.example.appcenter3.fragment.DoneFragment
import com.example.appcenter3.fragment.IngFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewTab : TabLayout = findViewById(R.id.viewTab)
        val viewPager:ViewPager2 = findViewById(R.id.viewPager)
        val pagerAdapter = PagerAdapter(this)
        viewPager.adapter = pagerAdapter

        val tabTitles = listOf<String>("시작 전", "진행 중", "완료")
        TabLayoutMediator(viewTab, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

}