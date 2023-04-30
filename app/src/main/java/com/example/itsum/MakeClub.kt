package com.example.itsum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.itsum.retrofit.APIService
import com.example.itsum.retrofit.postDataClass
import kotlinx.android.synthetic.main.activity_make_club.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MakeClub : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_make_club)

    makeClubCloseBtn.setOnClickListener{
      finish()
    }

    val recruitClassSpinner = findViewById<Spinner>(R.id.recruitClassSpinner)
    val recruitClassList = listOf("프로젝트","스터디")
    val recruitClassAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recruitClassList)

    recruitClassSpinner.adapter = recruitClassAdapter
    recruitClassSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
      }
      override fun onNothingSelected(p0: AdapterView<*>?) {
      }
    }

    val onOfflineSpinner = findViewById<Spinner>(R.id.onOffline)
    val onOfflineList = listOf("온라인","오프라인")
    val onOfflineAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, onOfflineList)
    onOfflineSpinner.adapter = onOfflineAdapter
    onOfflineSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
      }
      override fun onNothingSelected(p0: AdapterView<*>?) {
      }
    }

    val recruitMemberCount = findViewById<EditText>(R.id.recruitMemberCount)
    val recruitedMember = findViewById<EditText>(R.id.recruitedMember)
    val startDate = findViewById<EditText>(R.id.startDate)
    val expectedPeriod = findViewById<EditText>(R.id.expectedPeriod)
    val contactMethod = findViewById<EditText>(R.id.contactMethod)
    val recruitTitle = findViewById<EditText>(R.id.recruitTitle)
    val recruitExplain = findViewById<EditText>(R.id.recruitExplain)

    // 만약 새로 만들기로 들어온다 -> 데이터 없이 페이지 렌더링
    // 만약 수정하기로 들어온다 -> 서버에서 데이터 받아온 후 페이지 렌더링 및 데이터 뿌림

    var retrofit = Retrofit.Builder()
      .baseUrl("https://localhost:3306/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    var clubPostService: APIService = retrofit.create(APIService::class.java)



    createClubBtn.setOnClickListener{
      val title = recruitTitle.text.toString()
      val contents = recruitExplain.text.toString()
      val positionList = "백엔드, 프론트엔드"
      val personnel = recruitMemberCount.text.toString().toInt()
      val techSkill = "자바, 코틀린, aws"
      val meetingWays = contactMethod.text.toString()
      clubPostService.requestClubPost(title,contents,positionList,personnel,techSkill,meetingWays).enqueue(object : Callback<postDataClass> {
        override fun onFailure(call: Call<postDataClass>, t: Throwable) {
          Log.d("통신실패", "메세지:"+t.message)
        }

        override fun onResponse(call: Call<postDataClass>, response: Response<postDataClass>) {
          var res = response.body()
          Log.d("통신성공","제목"+res?.title)
        }
      })
    }
  }
}