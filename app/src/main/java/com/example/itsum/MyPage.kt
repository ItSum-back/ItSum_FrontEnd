package com.example.itsum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.itsum.retrofit.APIService
import com.example.itsum.retrofit.ATM
import com.example.itsum.retrofit.ClubSearchResponse
import com.example.itsum.retrofit.UserNameChangeResponse
import kotlinx.android.synthetic.main.activity_my_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPage : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_my_page)

    var atm = ATM
    var api = APIService.create()

    var isInsBtnClicked = false // 수정 버튼 클릭 상태 확인

    userName.setText(atm.getName())

    userNameInsBtn.setOnClickListener{
      if(!isInsBtnClicked){
        isInsBtnClicked = true
        userName.visibility = View.GONE
        userNameEditView.visibility = View.VISIBLE
        userNameEditView.setText(atm.getName())
        userNameInsBtn.setText("확인")
      }
      else{
        var editedUserName = userNameEditView.text.toString()
        api.requestUserNameChange("Bearer ${atm.getToken()}", atm.getId(), editedUserName).enqueue(object : Callback<UserNameChangeResponse>{
          override fun onFailure(call: Call<UserNameChangeResponse>, t: Throwable) {
            println("닉네임 변경 실패 "+t.message)
          }
          override fun onResponse(
            call: Call<UserNameChangeResponse>,
            response: Response<UserNameChangeResponse>
          ) {
            isInsBtnClicked = false
            Toast.makeText(applicationContext, "닉네임이 변경되었습니다. "+editedUserName,Toast.LENGTH_SHORT).show()
            userNameEditView.visibility = View.GONE
            userName.visibility = View.VISIBLE
            userName.setText(editedUserName)
            atm.setName(editedUserName)
            userNameInsBtn.setText("수정하기")
          }
        })
      }
    }

    api.searchUsingGet("Bearer"+atm.getToken(),members = atm.getName()).enqueue(object : Callback<ClubSearchResponse>{
      override fun onFailure(call: Call<ClubSearchResponse>, t: Throwable) {
        println("내 모임 불러오기 실패 " + t.message)
      }
      override fun onResponse(
        call: Call<ClubSearchResponse>,
        response: Response<ClubSearchResponse>
      ) {
        val adapter = myClubRecyclerAdapter(response.body()?.data?.content)
        myClubRecycler.adapter = adapter
      }
    })

  }
}