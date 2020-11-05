package com.example.a3rdseminar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.lang.IllegalStateException

class SampleViewPagerAdapter (fm : FragmentManager)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = when(position) {
        0 -> FirstFragment()
        1 -> SecondFragment()
        2 -> ThirdFragment()
        else -> throw IllegalStateException("Unexpected position $position")
    }

    override fun getCount(): Int = 3
}