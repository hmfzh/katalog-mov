package com.d3if0028.katalogmov.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.adapter.TabAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val tabAdapter = TabAdapter(supportFragmentManager,lifecycle)
        view_pager.adapter = tabAdapter

        val tabTitles = arrayOf("Popular" , "Now Playing")
        TabLayoutMediator(tab_Layout,view_pager){
            tab,position -> tab.text = tabTitles[position]
        }.attach()
    }
}