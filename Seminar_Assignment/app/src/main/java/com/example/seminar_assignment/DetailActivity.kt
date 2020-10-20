package com.example.seminar_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val detail_title = intent.getStringExtra("title")
        val detail_subtitle = intent.getStringExtra("subtitle")
        val detail_content = intent.getStringExtra("content")

        activity_detail_tv_title.text = detail_title.toString()
        activity_detail_tv_subtitle.text = detail_subtitle.toString()
        activity_detail_tv_content.text = detail_content.toString()
    }
}