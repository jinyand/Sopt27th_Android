# 📁 Seminar_Assignment

### [1주차 필수 과제] SignUpActivity 만들기

* 로그인 화면에서 회원가입을 누르면 이동
```kotlin
/* MainActivity */
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
```

* 모든 EditTextView에 데이터가 있을 경우 -> 회원가입 완료 Toast Message  
하나라도 없을 경우 -> 빈 칸이 있다는 Toast Message
```kotlin
/* SignUpActivity */
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
```
* 비밀번호 EditText는 입력 내용이 가려져야 함 (inputType)
* 모든 EditTextView는 미리보기 글씨가 있어야 함 (Hint)
```xml
<EditText
        android:id="@+id/et_signup_pw"
        android:inputType="textPassword"
        android:hint="비밀번호"
        ... />
```

### [1주차 성장 과제1] startActivityForResult()

### [1주차 성장 과제2] 자동로그인 - SharedPreferences()
