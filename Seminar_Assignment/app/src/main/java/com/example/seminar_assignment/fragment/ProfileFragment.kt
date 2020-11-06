package com.example.seminar_assignment.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.seminar_assignment.adapter.ProfileTabAdapter
import com.example.seminar_assignment.R
import com.example.seminar_assignment.activity.MainActivity
import com.example.seminar_assignment.etc.MySharedPreferences
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 로그아웃 버튼
        fragment_profile_btn_logout.setOnClickListener {
            MySharedPreferences.clearUser(view.context)
            val intent = Intent(view.context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        val tabLayout = view.findViewById(R.id.fragment_profile_tab) as TabLayout
        val viewPager = view.findViewById(R.id.fragment_profile_vp) as ViewPager
        val tabAdapter = ProfileTabAdapter(childFragmentManager)

        viewPager.adapter = tabAdapter
        tabAdapter.notifyDataSetChanged()

        tabLayout.setupWithViewPager(viewPager)
        tabLayout.apply {
            getTabAt(0)?.text = "info"
            getTabAt(1)?.text = "other"
        }

    }

}