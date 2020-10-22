# 📁 Seminar_Assignment
- [1차 세미나 과제](#-20201016-1%EC%B0%A8-%EC%84%B8%EB%AF%B8%EB%82%98-%EA%B3%BC%EC%A0%9C)
- [2차 세미나 과제](#-20201021-2%EC%B0%A8-%EC%84%B8%EB%AF%B8%EB%82%98-%EA%B3%BC%EC%A0%9C)
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

⏫ [TOP](#-seminar_assignment)

<br>

## ⚡ 2020/10/21 2차 세미나 과제

* 구현 화면


### [2주차 필수 과제] RecyclerView 만들기
* __RecyclerView__  
RecyclerView는 사용자가 관리하는 많은 수의 데이터 집합(Data Set)을 개별 아이템 단위로 구성하여 화면에 출력하는 뷰그룹(ViewGroup)이며, 한 화면에 표시되기 힘든 많은 수의 데이터를 스크롤 가능한 리스트로 표시해주는 위젯이다.  

* LayoutManager를 사용하여 다양한 뷰 배치를 표현할 수 있어서 유연하다는 장점이 있다.  
    * LinearLayoutManager : 세로/가로방향 배치  
    * GridLayoutManager : 바둑판 형식 배치  

<br>

* RecyclerView의 사용 방식은 다음과 같다.

0. __라이브러리 추가__ (build.gradle - app)
    ```
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    ```

1. __ItemView (xml)__ - 반복될 뷰를 만든다.  
앞으로 재사용될 레이아웃 파일을 생성한다.  

2. __Data class__ - 데이터 형태를 정의하는 class를 생성한다.  
    ```kotlin
    data class ProfileData(
        val title : String,
        val subTitle : String,
        val content : String,
        val date : String
    )
    ```

3. __ViewHolder__ - 받은 데이터를 뷰로 연결시켜준다.  
ViewHolder란 각 뷰들을 보관하는 홀더 객체이다. 각 뷰 객체를 ViewHolder에 보관함으로써 findViewById 같은 반복적으로 호출되는 메서드를 효과적으로 줄여 속도를 향상시킨다.
    ```kotlin
    class ProfileViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val title : TextView = itemView.findViewById(R.id.tv_title)
        private val subTitle : TextView = itemView.findViewById(R.id.tv_subtitle)

        // ViewHolder와 data class의 각 변수를 연동하는 역할
        fun onBind(data : ProfileData) {
            title.text = data.title
            subTitle.text = data.subTitle
        }
    }
    ```

4. __Adapter__ - RecyclerView에 표시될 아이템 뷰를 생성한다.  
Adapter는 필요에 따라 ViewHolder를 만들고, 데이터와 바인딩함으로써 ViewHolder를 특정 위치에 할당한다.  
RecyclerView의 Adapter에서 꼭 구현해야 하는 것은 다음과 같다.  

    | 메서드 | 설명 |
    |:---|:---|
    | onCreateViewHolder(ViewGroup parent, int viewType) | viewType 형태의 아이템 뷰를 위한 뷰홀더 객체 생성 |
    | onBindViewHolder(ViewHolder holder, int position) | position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시 |
    | getItemCount() | 전체 아이템 갯수 리턴 |

    ```kotlin
    class ProfileAdapter (private var context : Context) : RecyclerView.Adapter<ProfileViewHolder>() {

        var data = mutableListOf<ProfileData>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_home_recycler, parent, false)
            return ProfileViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
            holder.onBind(data[position])
        }

        override fun getItemCount(): Int = data.size
    }
    ```
    > onCreateViewHolder 메서드에서 LayoutInflater를 이용하여 item xml을 inflate 시킨다.  
    (참고) inflate란? xml에 쓰여있는 view의 정의를 실제 view객체로 만드는 역할

5. __RecyclerView__ - 마지막으로 데이터를 넣고, Adapter을 이용해서 RecyclerView에 띄워준다.  
데이터를 추가했으면 notifyDataSetChanged()를 통해 데이터가 갱신됨을 어댑터에 알려주어야 한다.
    ```kotlin
    class HomeActivity : AppCompatActivity() {
        private lateinit var profileAdapter: ProfileAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)

            ...

            profileAdapter = ProfileAdapter(this)

            activity_home_rv_profile.adapter = profileAdapter
            activity_home_rv_profile.layoutManager = LinearLayoutManager(this)

            profileAdapter.data = mutableListOf(
                ProfileData("title", "subtitle", "content", "date"),
                ProfileData("title", "subtitle", "content", "date"),
                ProfileData("title", "subtitle", "content", "date"),
                ProfileData("title", "subtitle", "content", "date"),
                ProfileData("title", "subtitle", "content", "date"),
                ProfileData("title", "subtitle", "content", "date") // 임의로 넣어준다
            )

            profileAdapter.notifyDataSetChanged()

        }
    }
    ```

<br>

### [2주차 필수 과제] 상세보기 화면 만들기 - Item Click Listener
📝 각 아이템을 클릭하면 해당 아이템의 정보를 가지고 있는 상세화면으로 이동합니다.  
📝 상세보기 화면에서 보여줘야 할 것   1. Title 2. SubTitle 3. 작성날짜 4. 부가설명

* RecyclerView Item Click  
Adapter의 onBindViewHolder에서 itemView에 setOnClickListener를 걸어주고 원하는 작업을 수행한다.

* Pass data to Activity
Item Click을 통해 새로운 액티비티(여기서는 상세보기 화면)로 이동했다면, 해당 item의 정보도 같이 넘겨줄 예정이다.  
이 경우 Intent의 putExtra()를 사용하여 값을 보낸다.  
그리고 받아오고자 하는 화면에서 getStringExtra()를 사용하여 값을 받아온다.  

    ```kotlin
    /* Adapter */
    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.onBind(data[position])

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("title", data[position].title)
            intent.putExtra("subtitle", data[position].subTitle)
            intent.putExtra("content", data[position].content)
            intent.putExtra("date", data[position].date)
            startActivity(holder.itemView.context, intent, null)

        }
    }
    ```
    
    ```kotlin
    /* DetailActivity */
    val detail_title = intent.getStringExtra("title")
    val detail_subtitle = intent.getStringExtra("subtitle")
    val detail_content = intent.getStringExtra("content")
    val detail_date = intent.getStringExtra("date")

    activity_detail_tv_title.text = detail_title.toString()
    activity_detail_tv_subtitle.text = detail_subtitle.toString()
    activity_detail_tv_content.text = detail_content.toString()
    activity_detail_tv_date.text = detail_date.toString()
    ```

<br>
 
### [2주차 성장 과제1] GridLayout 만들기
📝 필수 과제로 만든 아이템을 격자 형태로 바꾸기

* GridLayoutManager(context, 한 줄에 들어가는 아이템 개수, RecyclerView.VERTICAL, false)
    ```kotlin
    activity_home_grid_rv_profile.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
    ```

<br>
    
### [2주차 성장 과제2] RecyclerView Item 이동 삭제 구현
📝 아이템을 길게 누르면 위치를 바꿀 수 있음  
📝 옆으로 슬라이드 하면 아이템이 삭제됨

1. __ItemTouchHelper__  
    * ItemTouchHelper는 RecyclerView.ItemDecoration의 서브 클래스이다. RecyclerView 및 Callback 클래스와 함께 작동하며, 사용자가 이러한 액션을 수행할 때 이벤트를 수신한다.
    * ItemTouchHelper.Callback은 추상 클래스로, 추상 메서드인 getMovementFlags(), onMove(), onSwiped()를 필수로 재정의해야 한다. 아니면 Wrapper 클래스인 ItemTouchHelper.SimpleCallback을 이용해도 된다.
    
* getMovementFlags() : Drag 및 Swipe 이벤트의 방향을 설정한다.
* onMove() : 아이템이 Drag 되면 ItemTouchHelper는 onMove()를 호출한다.  
이때 ItemActionListener로 어댑터에 fromPosition과 toPosition을 파라미터와 함께 콜백을 전달한다.
* onSwiped() : 아이템이 Swipe 되면 ItemTouchHelper는 범위를 벗어날 때까지 애니메이션을 적용한 후 onSwiped()를 호출한다.  
이때 ItemActionListener로 어댑터에 제거할 아이템의 position을 파라미터와 함께 콜백을 전달한다.
* isLongPressDragEnabled(), isItemViewSwipeEnabled() : 아이템을 길게 누르거나 스와이프하면 Drag & Drop 또는 Swipe 작업을 시작해야 하는지를 반환한다.

```kotlin
class ItemMoveCallback constructor(val profileAdapter: ProfileAdapter) : ItemTouchHelper.Callback(){

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val flagDrag = ItemTouchHelper.UP or ItemTouchHelper.DOWN    //드래그 앤 드롭 움직임 설정
        val flagSwipe = ItemTouchHelper.START or ItemTouchHelper.END // 스와이프 움직임 설정
        return makeMovementFlags(flagDrag, flagSwipe)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        profileAdapter.onItemDragMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        profileAdapter.onItemRemoved(viewHolder.adapterPosition)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

}
```

2. __Adapter에서 메소드 구현__  

* onItemDragMove : 현재위치와 움직일 위치를 입력받고 아이템의 이동을 구현한다. notifyItemMoved 메소드를 사용하여 데이터가 이동함을 알린다.
* onItemRemoved : 위치값을 입력받고 아이템리스트의 해당 포지션 아이템을 삭제한다. notifyItemRemoved 메소드를 사용하여 데이터가 삭제되었음을 알린다.

```kotlin
class ProfileAdapter (private var context : Context) : RecyclerView.Adapter<ProfileViewHolder>() {

    ...
    
    // 순서를 변경하는 함수
    fun onItemDragMove(beforePosition : Int, afterPosition : Int){
        if(beforePosition < afterPosition){
            for (i in beforePosition until afterPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in beforePosition downTo afterPosition + 1) {
                Collections.swap(data, i, i - 1)
            }
        }

        notifyItemMoved(beforePosition, afterPosition)
        notifyDataSetChanged()
    }

    // 아이템을 삭제하는 함수
    fun onItemRemoved(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

}
```

3. __적용하기__
* 액티비티에서는 adapter를 입력한 ItemMoveCallback 클래스를 ItemTouchHelper 생성자에 입력하여 생성한다.
* TouchHelper의 attachToRecyclerView 메소드를 활용하여 Touch를 구현할 리사이클러뷰를 연결한다.
```kotlin
val callback = ItemMoveCallback(profileAdapter)
val touchHelper = ItemTouchHelper(callback)
touchHelper.attachToRecyclerView(activity_home_rv_profile)
```

⏫ [TOP](#-seminar_assignment)

<br>
