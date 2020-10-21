package com.example.seminar_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_home_grid.*

class HomeGridActivity : AppCompatActivity() {
    private lateinit var profileGridAdapter: ProfileGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_grid)

        profileGridAdapter = ProfileGridAdapter(this)

        activity_home_grid_rv_profile.adapter = profileGridAdapter
        activity_home_grid_rv_profile.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)

        profileGridAdapter.data = mutableListOf(
            ProfileData("SWU", "Information Security", "2016. 03 ~ 2021. 02\nSWU\nDept. of Information Security"),
            ProfileData("SOPT", "26th YB", "2020. 03 ~ 2020. 07\n26기 안드로이드파트 YB"),
            ProfileData("MONGLE", "Android Developer", "2020. 07\nAndroid Developer\n몽글 ♡"),
            ProfileData("SOPT", "27th OB", "2020. 09 ~ 2021. 01\n27기 안드로이드파트 OB"),
            ProfileData("STUDY", "27th Study", "1. 안드로이드 심화스터디\n2. 기상 스터디\n3. 모각공 스터디\n4. 몽그리즘"),
            ProfileData("S.W.A.N", "Orchestra", "2016. 09 ~ 2018. 08\n악기 하고 싶다 ~~~~"),
        )

        profileGridAdapter.notifyDataSetChanged()
    }
}