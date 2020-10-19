package com.example.a2ndseminar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var sampleAdapter: SampleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sampleAdapter = SampleAdapter(this)

        rv_main.adapter = sampleAdapter
        rv_main.layoutManager = LinearLayoutManager(this)

        sampleAdapter.data = mutableListOf(
            SampleData("이름", "조현진"),
            SampleData("나이", "24"),
            SampleData("파트", "안드로이드"),
            SampleData("Github", "jinyand"),
            SampleData("Blog", "tistory"),
            SampleData("Sopt", "www.sopt.org")
        )

        sampleAdapter.notifyDataSetChanged()

    }
}