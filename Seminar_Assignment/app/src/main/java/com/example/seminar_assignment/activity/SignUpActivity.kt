package com.example.seminar_assignment.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.seminar_assignment.R
import com.example.seminar_assignment.SampleInterface
import com.example.seminar_assignment.network.RequestSignUpData
import com.example.seminar_assignment.network.ResponseSignUpData
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_signup.setOnClickListener {
            if(et_signup_name.text.isNotEmpty() && et_signup_id.text.isNotEmpty() && et_signup_pw.text.isNotEmpty()) {
                SignUp()
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

        changeArea(et_signup_name)
        changeArea(et_signup_id)
        changeArea(et_signup_pw)

    }

    private fun changeArea(edittext : EditText) {
        edittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                edittext.background = resources.getDrawable(R.drawable.login_et_yellow, null)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun SignUp() {
        SampleInterface.service.postSignUp(
            RequestSignUpData(
                email = et_signup_id.text.toString(),
                password = et_signup_pw.text.toString(),
                userName = et_signup_name.text.toString()
            )
        ).enqueue(
            object : Callback<ResponseSignUpData> {
                override fun onResponse(
                    call: Call<ResponseSignUpData>,
                    response: Response<ResponseSignUpData>,
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@SignUpActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                        intent.putExtra("id", et_signup_id.text.toString())
                        intent.putExtra("passwd", et_signup_pw.text.toString())
                        setResult(RESULT_OK, intent)
                        finish()
                    } else {
                        Toast.makeText(this@SignUpActivity, "이미 존재하는 이메일입니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseSignUpData>, t: Throwable) {
                    Log.d("회원가입 통신 실패", "$t")
                }

            }
        )
    }
}