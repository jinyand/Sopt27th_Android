package com.example.seminar_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_home_recycler.*

/* 2차 세미나 과제 - RecyclerView */
class HomeActivity : AppCompatActivity() {
    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        activity_home_btn_logout.setOnClickListener {
            MySharedPreferences.clearUser(this)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        activity_home_btn_go_grid.setOnClickListener {
            val intent = Intent(this, HomeGridActivity::class.java)
            startActivity(intent)
        }

        profileAdapter = ProfileAdapter(this)

        activity_home_rv_profile.adapter = profileAdapter
        activity_home_rv_profile.layoutManager = LinearLayoutManager(this)

        profileAdapter.data = mutableListOf(
            ProfileData("SWU", "Information Security", "2016. 03 ~ 2021. 02\nSWU\nDept. of Information Security"),
            ProfileData("SOPT", "26th YB", "2020. 03 ~ 2020. 07\n26기 안드로이드파트 YB"),
            ProfileData("MONGLE", "Android Developer", "2020. 07\nAndroid Developer\n몽글 ♡"),
            ProfileData("SOPT", "27th OB", "2020. 09 ~ 2021. 01\n27기 안드로이드파트 OB"),
            ProfileData("STUDY", "27th Study", "1. 안드로이드 심화스터디\n2. 기상 스터디\n3. 모각공 스터디\n4. 몽그리즘"),
            ProfileData("S.W.A.N", "Orchestra", "2016. 09 ~ 2018. 08\n악기 하고 싶다 ~~~~"),
        )

        profileAdapter.notifyDataSetChanged()

    }
}