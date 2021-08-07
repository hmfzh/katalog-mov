package com.d3if0028.katalogmov.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.d3if0028.katalogmov.fragment.NowPlayingFragment
import com.d3if0028.katalogmov.fragment.PopularFragment

class TabAdapter(fragmentManger:FragmentManager,lifecycle:Lifecycle)
    :FragmentStateAdapter(fragmentManger,lifecycle) {

    val fragments : ArrayList<Fragment> = arrayListOf(
        PopularFragment(),
        NowPlayingFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}