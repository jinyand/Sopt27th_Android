package com.example.seminar_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tv_users_id.text = "아이디 : " + MySharedPreferences.getId(this)
        tv_users_pw.text = "비밀번호 : " + MySharedPreferences.getPw(this)

        btn_logout.setOnClickListener {
            MySharedPreferences.clearUser(this)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}