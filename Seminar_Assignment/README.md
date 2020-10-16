# 📁 Seminar_Assignment

## ⚡ 2020/10/16 1차 세미나 과제

* 구현 화면

![image](https://user-images.githubusercontent.com/38918396/96242594-990f3880-0fde-11eb-8c42-939e3d285a86.png)

### [1주차 필수 과제] SignUpActivity 만들기

📝 로그인 화면에서 회원가입을 누르면 이동
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

📝 모든 EditTextView에 데이터가 있을 경우 -> 회원가입 완료 Toast Message  
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
📝 비밀번호 EditText는 입력 내용이 가려져야 함 (inputType)  
📝 모든 EditTextView는 미리보기 글씨가 있어야 함 (Hint)
```xml
<EditText
        android:id="@+id/et_signup_pw"
        android:inputType="textPassword"
        android:hint="비밀번호"
        ... />
```

### [1주차 성장 과제1] startActivityForResult()

📝 회원 가입에 성공한다면 이전 로그인 화면으로 이동  
📝 이 때, 아이디와 비밀번호가 입력되어 있어야 함

💡 __startActivityForResult__<br>
startActivityForResult는 이동된 Activity로부터 값을 가져올때 쓸 수 있다.

**1. _MainActivity.kt_ - 값을 받아오고자하는 Activity를 호출한다.**  
                        - 여기서는 MainActivity에서 SignUpActivity를 startActivityForResult로 호출했다.
```kotlin
val intent = Intent(this, SignUpActivity::class.java)
startActivityForResult(intent, REQUEST_CODE)
```

**2. _SignUpActivity.kt_ - 호출된 Activity**  
(1) intent.putExtra(key, value) - 전달하고자 하는 값  
(2) setResult() - RESULT_OK와 값이 담긴 intent를 전달  
(3) finish() - Activity를 종료하고 다시 MainActivity(이전 Login화면)으로 돌아감  
```kotlin
val intent = Intent(this, MainActivity::class.java)
intent.putExtra("id", et_signup_id.text.toString())
intent.putExtra("passwd", et_signup_pw.text.toString())
setResult(RESULT_OK, intent)
finish()
```

**3. _LoginActivity.kt_ - onActivityResult에서 받아온 값 확인**
- 결과를 받아오기 위해서는 요청한 액티비티에서 onActivityResult()라는 메서드를 구현해주어야한다.
- 3개의 인자 : requestCode, resultCode, data  
            - requestCode : 1번에서 액티비티를 띄울 때 전달했던 요청 코드  
            - resultCode : 새로 띄운 액티비티에서 전달된 결과의 성공여부 (RESULT_OK, RESULT_CANCELED)  
            - data : 전달받은 인텐트  
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
        val id = data!!.getStringExtra("id")
        val passwd = data!!.getStringExtra("passwd")
        et_login_id.setText(id)
        et_login_pw.setText(passwd)
    }
}
```

### [1주차 성장 과제2] 자동로그인 - SharedPreferences()
