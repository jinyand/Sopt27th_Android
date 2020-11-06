package com.example.seminar_assignment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.seminar_assignment.fragment.ProfileFragment
import com.example.seminar_assignment.fragment.WidgetFragment
import com.example.seminar_assignment.fragment.PortfolioFragment
import java.lang.IllegalStateException

class HomeViewPagerAdapter (fm : FragmentManager)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = when(position) {
        0 -> ProfileFragment()
        1 -> PortfolioFragment()
        2 -> WidgetFragment()
        else -> throw IllegalStateException("Unexpected position $position")
    }

    override fun getCount(): Int = 3
}