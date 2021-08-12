package com.example.newprovalyuta.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newprovalyuta.fragments.ListFragment
import com.example.newprovalyuta.fragments.SavedFragment

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            SavedFragment.newInstance(position.toString(), "")
        } else {
            ListFragment.newInstance(position.toString())
        }
    }
}