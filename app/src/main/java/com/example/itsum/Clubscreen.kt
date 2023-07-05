package com.example.itsum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    val socialId = atm.getId()

    // api 호출이 안됨. 바로 위까지는 작업이 완료 잘 됨. 로그 확인 가능
    api.requestClubData("Bearer "+at, id).enqueue(object :Callback<ClubGetData>{
      override fun onFailure(call: Call<ClubGetData>, t: Throwable) {
        val goHomeIntent = Intent(this@Clubscreen, Home::class.java)
        finish()
        startActivity(goHomeIntent)
      }
      override fun onResponse(call: Call<ClubGetData>, response: Response<ClubGetData>) {
        val res = response.body()?.data
        if(res != null) { // 각 데이터 표시
          TitleView.setText(res.title)
          userNameView.setText(res.members)
          CreatedAtView.setText(res.createdAt!!.substring(0,9))
          categoryView.setText(res.category)
          contactView.setText(res.contact)
          personnelView.setText(res.personnel.toString())
          meetingWayView.setText(res.meetingWay)
          startTimeView.setText(res.projectStartTime!!.substring(0,9))
          endTimeView.setText(res.projectEndTime!!.substring(0,9))
          deadlineView.setText(res.deadline!!.substring(0,9))
          positionListView.setText(res.positionList)
          techskillView.setText(res.techSkill)
          screencontents.setText(res.contents)
        }
        if(socialId == res!!.socialId) { // 유저와 생성자 아이디 불일치 시 수정, 삭제 버튼 가리기
          Insbtn.visibility = View.VISIBLE
          Insbtn.isEnabled = false
          Dltbtn.visibility = View.VISIBLE
          Dltbtn.isEnabled = false
        }
        else {
          TitleView.setText("문제가 생겼습니다.")
        }
      }
    })

    fun getComment() {
      api.requestCommentList("Bearer "+at, id).enqueue(object :Callback<CommentGetResponse>{
        override fun onResponse(
          call: Call<CommentGetResponse>,
          response: Response<CommentGetResponse>
        ) {if (response.body()?.data?.data?.content != null) {
          println(response.body()?.data?.data?.content)
          val adapter = CommentRecyclerAdapter(response.body()?.data?.data?.content)
          CommentList.adapter = adapter
        }
        }
        override fun onFailure(call: Call<CommentGetResponse>, t: Throwable) {
          println("코멘트 데이터 없음" + t.message)
        }
      })
    }

    getComment()

    clubScreenCloseBtn.setOnClickListener{
      finish()
    }
    Insbtn.setOnClickListener{
      val makeClubIntent = Intent(this, MakeClub::class.java)
      startActivity(makeClubIntent)
      finish()
    }
    Dltbtn.setOnClickListener{
      api.DeleteClubData("Bearer "+at, id)
    }

    // 댓글 작성
    commentsubmit.setOnClickListener{
      if(commentEdit.text.toString() !== ""){
        val data = postComment(commentEdit.text.toString(), atm.getName(), id, atm.getId())
        api.postComment("Bearer "+at, id, data).enqueue(object :Callback<postCommentResponse>{
          override fun onFailure(call: Call<postCommentResponse>, t: Throwable) {
            println("코멘트 작성 응답 오류")
          }
          override fun onResponse(
            call: Call<postCommentResponse>,
            response: Response<postCommentResponse>
          ) {
            commentEdit.setText("")
            getComment()
          }
        })
      }
    }
  }
}