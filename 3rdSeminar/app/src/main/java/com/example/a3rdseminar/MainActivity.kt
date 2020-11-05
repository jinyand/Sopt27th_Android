package com.example.a3rdseminar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var viewPagerAdapter: SampleViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 뷰페이저 세팅

        viewPagerAdapter = SampleViewPagerAdapter(supportFragmentManager)
        sample_vp.adapter = viewPagerAdapter

        // 뷰페이저를 슬라이드 했을 때 그에 대응되는 하단 탭으로 전환
        sample_vp.addOnPageChangeListener(object  : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                sample_bottom_navi.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })


        // 바텀 네비게이션 세팅
        sample_bottom_navi.setOnNavigationItemSelectedListener {
            var index by Delegates.notNull<Int>()

            when(it.itemId) {
                R.id.menu_account -> index = 0
                R.id.menu_check -> index = 1
                R.id.menu_fire -> index = 2
            }

            // 하단 아이템을 클릭했을 때 대응되는 뷰페이저 화면으로 전환
            sample_vp.currentItem = index

            true
        }

        // 탭 레이아웃 연동
        sample_tab.setupWithViewPager(sample_vp)
        sample_tab.apply {
            getTabAt(0)?.text = "first"
            getTabAt(1)?.text = "second"
            getTabAt(2)?.text = "third"
        }

    }
}