package com.example.seminar_assignment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.seminar_assignment.fragment.InfoFragment
import com.example.seminar_assignment.fragment.OtherFragment
import java.lang.IllegalStateException

class ProfileTabAdapter (fm: FragmentManager)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment = when(position) {
        0 -> InfoFragment()
        1 -> OtherFragment()
        else -> throw IllegalStateException("Unexpected position $position")
    }

}