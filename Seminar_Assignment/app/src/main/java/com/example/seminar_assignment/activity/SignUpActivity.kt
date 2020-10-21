package com.example.seminar_assignment.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.seminar_assignment.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_signup.setOnClickListener {
            if(et_signup_name.text.isNotEmpty() && et_signup_id.text.isNotEmpty() && et_signup_pw.text.isNotEmpty()) {
                Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", et_signup_id.text.toString())
                intent.putExtra("passwd", et_signup_pw.text.toString())
                setResult(RESULT_OK, intent)
                finish()
            } else {
                if(et_signup_name.text.isEmpty()) {
                    Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else if(et_signup_id.text.isEmpty()) {
                    Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else if(et_signup_pw.text.isEmpty()) {
                    Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}