package com.example.seminar_assignment.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.seminar_assignment.adapter.HomeViewPagerAdapter
import com.example.seminar_assignment.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlin.properties.Delegates

class HomeActivity : AppCompatActivity() {
    private lateinit var viewPagerAdapter: HomeViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewPagerAdapter = HomeViewPagerAdapter(supportFragmentManager)
        activity_home_vp.adapter = viewPagerAdapter

        // 뷰페이저 세팅
        activity_home_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                activity_home_bn.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        // 바텀 네비게이션 세팅
        activity_home_bn.setOnNavigationItemSelectedListener {
            var index by Delegates.notNull<Int>()

            when(it.itemId) {
                R.id.menu_profile -> index = 0
                R.id.menu_portfolio -> index = 1
                R.id.menu_widgets -> index = 2
            }

            activity_home_vp.currentItem = index

            true
        }

    }
}