package com.example.itsum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.itsum.databinding.ActivityMakeClubBinding
import com.example.itsum.retrofit.*
import kotlinx.android.synthetic.main.activity_clubscreen.*
import kotlinx.android.synthetic.main.activity_make_club.*
import kotlinx.android.synthetic.main.activity_make_club.deadlineView
import kotlinx.android.synthetic.main.activity_make_club.positionListView
import retrofit2.*

class MakeClub : AppCompatActivity() {
  private var _binding: ActivityMakeClubBinding? = null
  private val binding get() = _binding!!
  val api = APIService.create()
  val at = ATM
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMakeClubBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val id = intent.getIntExtra("postId", 0)
    val isIns = intent.getBooleanExtra("isIns", false)

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

    val category = binding.recruitClassSpinner //모집구분
    val contact = binding.onOffline // 진행방식
    val contents = binding.contents // 내용
    val deadline = binding.deadline // 데드라인
    val meetingWays = binding.meetingWays // 연락방법
    val personnel = binding.personnel // 모집인원
    val positionList = binding.positionList// 포지션리스트
    val projectStartTime = binding.projectStartTime // 프로젝트 시작일시
    val projectendtime = binding.projectEndTime // 프로젝트 종료일시
    val techSkill = binding.techSkill // 테크스킬
    val title = binding.title // 제목

    // 만약 새로 만들기로 들어온다 -> 데이터 없이 페이지 렌더링

    if(isIns) { // 수정하기로 들어왔다면? -> 데이터 호출
      api.requestClubData("Bearer "+at.getToken(), id).enqueue(object :Callback<ClubGetData>{
        override fun onFailure(call: Call<ClubGetData>, t: Throwable) {
          val goHomeIntent = Intent(this@MakeClub, Home::class.java)
          finish()
          startActivity(goHomeIntent)
        }
        override fun onResponse(call: Call<ClubGetData>, response: Response<ClubGetData>) {
          val res = response.body()?.data
          if(res != null) { // 각 데이터 표시
            title.setText(res.title)
            var categoryvalue = if(res.category==="프로젝트") 0 else 1
            category.setSelection(categoryvalue)
            var contactvalue = if(res.contact==="온라인") 0 else 1
            contact.setSelection(contactvalue)
            personnel.setText(res.personnel.toString())
            meetingWays.setText(res.meetingWay)
            projectStartTime.setText(res.projectStartTime!!.substring(0,9))
            projectendtime.setText(res.projectEndTime!!.substring(0,9))
            deadline.setText(res.deadline!!.substring(0,9))
            positionList.setText(res.positionList)
            techSkill.setText(res.techSkill)
            contents.setText(res.contents)
            createClubBtn.setText("수정하기")
          }
          else {
            TitleView.setText("문제가 생겼습니다.")
          }
        }
      })
    }

    // validation 파트 여기에 들어가야함
    // 후에 버튼 클릭 검증 필요
    // 먼저 버튼을 btn.enable === isEnable.disable? 정도로 비활성화 해주고  모든 칸이 알맞게 채워지면 비활성화된 버튼을 활성화 해주는 코드?

    binding.createClubBtn.setOnClickListener{ // 버튼 클릭시
      val category = category.selectedItem.toString()
      val contact = contact.selectedItem.toString()
      val contents = contents.text.toString()
      val deadline = deadline.text.toString()
      val meetingWays = meetingWays.text.toString()
      val members = at.getName()
      val personnel : Int = personnel.text.toString().toInt()
      val positionList = positionList.text.toString()
      val projectStartTime = projectStartTime.text.toString()
      val projectendtime = projectendtime.text.toString()
      val socialId = at.getId()
      val techSkill = techSkill.text.toString()
      val title = title.text.toString()

      val data = ClubPostData(category,contact,contents,deadline,meetingWays,members,personnel,positionList,projectStartTime,projectendtime,socialId,techSkill,title)
      val putdata = ClubPutData(category,contact,contents,deadline,meetingWays,members,personnel,positionList,projectStartTime,projectendtime,techSkill,title)
      // 수정하기로 들어온게 아니면 post, 수정하기로 들어왔으면 put
      if(!isIns){
        api.requestClubPost("Bearer ${at.getToken()}" ,data).enqueue(object : Callback<ClubPostResponse> {
          override fun onFailure(call: Call<ClubPostResponse>, t: Throwable) {
            val goHomeIntent = Intent(this@MakeClub, Home::class.java)
            finish()
            startActivity(goHomeIntent)
            println("모임 포스트 오류"+t.message)
          }

          override fun onResponse(call: Call<ClubPostResponse>, response: Response<ClubPostResponse>) {
            val clubScreenIntent = Intent(this@MakeClub, Clubscreen::class.java)
            clubScreenIntent.putExtra("id", response.body()?.data)
            finish()
            startActivity(clubScreenIntent)
          }

        })
      } else {
        api.requestClubPut("Bearer ${at.getToken()}",id, putdata).enqueue(object : Callback<ClubPutResponse>{
          override fun onFailure(call: Call<ClubPutResponse>, t: Throwable) {
            val goHomeIntent = Intent(this@MakeClub, Home::class.java)
            finish()
            startActivity(goHomeIntent)
            println("모임 수정 오류"+t.message)
          }

          override fun onResponse(
            call: Call<ClubPutResponse>,
            response: Response<ClubPutResponse>
          ) {
            val clubScreenIntent = Intent(this@MakeClub, Clubscreen::class.java)
            clubScreenIntent.putExtra("id", id)
            finish()
            startActivity(clubScreenIntent)
          }
        })

      }
    }
  }
}