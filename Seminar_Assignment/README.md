# 📁 Seminar_Assignment
- [1차 세미나 과제](#-20201016-1%EC%B0%A8-%EC%84%B8%EB%AF%B8%EB%82%98-%EA%B3%BC%EC%A0%9C)
<br>

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

<br>

### [1주차 성장 과제1] startActivityForResult()

📝 회원 가입에 성공한다면 이전 로그인 화면으로 이동  
📝 이 때, 아이디와 비밀번호가 입력되어 있어야 함

### 💡 __startActivityForResult__  
startActivityForResult는 이동된 Activity로부터 값을 가져올때 쓸 수 있다.

<br>

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

**3. _MainActivity.kt_ - onActivityResult에서 받아온 값 확인**
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

<br>

### [1주차 성장 과제2] 자동로그인 - SharedPreferences()

📝 로그인에 성공하는 순간 id와 password를 기억해서 다음 로그인 때 자동 로그인 (바로 HomeActivity로 이동)  
📝 자동로그인이 될 경우 자동로그인이 됐다는 메시지를 출력

### 💡 __SharedPreferences__  
SharedPreferences는 (key, value) 형태로 관리되며, 간단한 값 저장에 사용하기 좋다.  
데이터의 양이 많다면 서버나 DB의 형태로 저장을 하는 것이 좋지만, 초기 설정값이나 자동 로그인과 같이 간단한 데이터를 저장해야 하는 경우에는 SharedPreferences를 사용하여 쉽게 관리할 수 있다.

<br>

**1. MySharedPreferences.kt 파일 생성**
```kotlin
object MySharedPreferences {
    private val MY_ACCOUNT : String = "my_account"

    fun setId(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_ID", input)
        editor.apply()
    }

    fun getId(context: Context) : String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_ID", "").toString()
    }

    fun setPw(context: Context, input: String) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.putString("MY_PW", input)
        editor.apply()
    }

    fun getPw(context: Context) : String {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        return prefs.getString("MY_PW", "").toString()
    }

    fun clearUser(context: Context) {
        val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}
```

**1-1. SharedPreferences 생성**
```kotlin
val prefs : SharedPreferences = context.getSharedPreferences(MY_ACCOUNT, Context.MODE_PRIVATE)
val editor : SharedPreferences.Editor = prefs.edit()
```
(1) getSharedPreferences(key, mode)  
- MODE_PRIVATE : 자기 앱내에서만 사용할때 (0을 입력해도 된다)  
- MODE_WORLD_READABLE : 다른 앱에서 읽기 가능  
- MODE_WORLD_WRITEABLE : 다른 앱에서 쓰기 가능  

(2) 데이터를 기록하기 위해서는 SharedPreferences.Editor 인스턴스를 얻어야한다.  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;생성한 editor를 사용하여 데이터의 저장, 수정, 삭제가 가능하다.

**1-2. 데이터 저장**
```kotlin
editor.putString(key, value)
editor.apply() // 필수
```
Editor 객체를 통해 원하는 값을 key, value 형태로 입력하고 apply 해주면 데이터 저장이 완료된다.  
여기서 중요한 점은 값을 넣어주고 나서 apply를 꼭 !! 해주어야 한다는 점이다.  
 > 이전 과제에서는 editor.commit()을 사용했는데 최근 IDE에서 apply() 를 권장하길래 둘의 차이점을 알아보았다.  
 commit은 동기 처리, 리턴 값 반환 있음(boolean) / apply는 비동기 처리, 리턴 값 반환 없음(void) 의 차이가 있는데  
 apply를 쓰면 비동기 처리로 인해 저장 속도가 약 10~30배 까지 빨라진다고 한다.  
 
저장할 수 있는 데이터 종류는 boolean, int, float, long, string이 있다.  

**1-3. 저장된 데이터 불러오기**
```kotlin
prefs.getString(key, "")
```
SharedPreferences 객체 prefs를 통해 원하는 값의 key와 defaultValue(key 값이 null일 경우 반환할 값)를 파라미터로 하여 호출하면 값을 받아올 수 있다.

**1-4. 모든 데이터 삭제**
```kotlin
editor.remove(key)
editor.clear()
editor.apply()
```
remove(key) : key에 해당하는 데이터가 삭제됨  
clear() : SharedPreferences의 모든 데이터가 삭제됨  
마찬가지로 변경 후에는 apply를 해주어야 한다.

**2. 자동 로그인 구현**

```kotlin
/* MainActivity */
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
```
