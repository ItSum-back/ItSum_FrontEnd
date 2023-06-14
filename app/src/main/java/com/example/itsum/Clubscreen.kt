package com.example.itsum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.itsum.databinding.ActivityClubscreenBinding
import com.example.itsum.retrofit.*
import kotlinx.android.synthetic.main.activity_clubscreen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Clubscreen : AppCompatActivity() {
  private var _binding: ActivityClubscreenBinding? = null
  private val binding get() = _binding!!
  val api = APIService.create()
  var atm = ATM

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityClubscreenBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val id = intent.getIntExtra("id", 0)
    val at = atm.getToken()

    clubScreenCloseBtn.setOnClickListener{
      finish()
    }

    val categoryView = binding.categoryView
    val ContactView = binding.onofflineView
    val ContentsView = binding.contentsView
    val CreatedDateView = binding.createdDateView
    val MeetingWayView = binding.ContactView
    val membersView = binding.membersView
    val personnelView = binding.personnelView
    val projectStartDate = binding.startDateView
    val TitleView = binding.titleView


    // api 호출이 안됨. 바로 위까지는 작업이 완료 잘 됨. 로그 확인 가능
    api.requestClubData("Bearer "+at, id).enqueue(object :Callback<ClubGetData>{
      override fun onFailure(call: Call<ClubGetData>, t: Throwable) {
        val goHomeIntent = Intent(this@Clubscreen, Home::class.java)
        finish()
        startActivity(goHomeIntent)
      }
      override fun onResponse(call: Call<ClubGetData>, response: Response<ClubGetData>) {
        val res = response.body()?.data
        if(res != null) {
          categoryView.setText(res.category)
          ContactView.setText(res.contact)
          ContentsView.setText(res.contents)
          CreatedDateView.setText(res.createdAt!!.slice(0..9))
          MeetingWayView.setText(res.meetingWay)
          membersView.setText(res.members)
          personnelView.setText(res.personnel.toString())
          projectStartDate.setText(res.projectStartTime!!.slice(0..9))
          TitleView.setText(res.title)
        }
        else {
          TitleView.setText("문제가 생겼습니다.")
        }
      }
    })

    // 댓글 리사이클러뷰
    api.requestCommentList("Bearer "+at, id).enqueue(object :Callback<CommentGetResponse>{
      override fun onResponse(
        call: Call<CommentGetResponse>,
        response: Response<CommentGetResponse>
      ) {if (response.body()?.data?.data?.content != null) {
          val adapter = CommentRecyclerAdapter(response.body()?.data?.data?.content)
          CommentList.adapter = adapter
        }
      }
      override fun onFailure(call: Call<CommentGetResponse>, t: Throwable) {
        println("코멘트 데이터 없음" + t.message)
      }
    })

  }


}