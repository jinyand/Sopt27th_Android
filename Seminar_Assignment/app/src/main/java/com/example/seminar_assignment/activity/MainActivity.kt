package com.example.seminar_assignment.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.seminar_assignment.etc.MySharedPreferences
import com.example.seminar_assignment.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(MySharedPreferences.getId(this).isBlank() || MySharedPreferences.getPw(this).isBlank()) {
            // SharedPreferences에 값이 저장되어 있지 않을 때 -> 로그인
            login()
        } else {
            // SharedPreferences에 값이 저장되어 있을 때 -> 자동 로그인
            Toast.makeText(this, "자동로그인 되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_go_signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

    }

    private fun login() {
        btn_login.setOnClickListener {
            if(et_login_id.text.isNullOrBlank() || et_login_pw.text.isNullOrBlank()) {
                Toast.makeText(this, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // SharedPreferences에 값 저장
                MySharedPreferences.setId(this, et_login_id.text.toString())
                MySharedPreferences.setPw(this, et_login_pw.text.toString())

                Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val id = data!!.getStringExtra("id")
            val passwd = data!!.getStringExtra("passwd")
            et_login_id.setText(id)
            et_login_pw.setText(passwd)
        }
    }

}