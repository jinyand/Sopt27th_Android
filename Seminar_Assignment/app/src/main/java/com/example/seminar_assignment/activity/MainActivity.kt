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
import com.example.seminar_assignment.etc.MySharedPreferences
import com.example.seminar_assignment.R
import com.example.seminar_assignment.SampleInterface
import com.example.seminar_assignment.network.RequestLoginData
import com.example.seminar_assignment.network.ResponseLoginData
import com.example.seminar_assignment.network.SampleService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        changeArea(et_login_id)
        changeArea(et_login_pw)

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

    private fun login() {
        btn_login.setOnClickListener {
            if(et_login_id.text.isNullOrBlank() || et_login_pw.text.isNullOrBlank()) {
                Toast.makeText(this, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            } else {

                SampleInterface.service.postLogin(
                    RequestLoginData(
                        email = et_login_id.text.toString(),
                        password = et_login_pw.text.toString()
                    )
                ).enqueue(
                    object : Callback<ResponseLoginData> {
                        override fun onResponse(
                            call: Call<ResponseLoginData>,
                            response: Response<ResponseLoginData>,
                        ) {
                            if(response.isSuccessful) {
                                if(response.body()!!.status == 200) {
                                    // SharedPreferences에 값 저장
                                    MySharedPreferences.setId(applicationContext, et_login_id.text.toString())
                                    MySharedPreferences.setPw(applicationContext, et_login_pw.text.toString())

                                    val user = response.body()!!.data.userName

                                    Toast.makeText(applicationContext, user +"님 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(applicationContext, HomeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(applicationContext, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
                                }

                            }
                        }

                        override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {
                            Log.d("로그인 통신 실패", "$t")
                        }

                    }
                )

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