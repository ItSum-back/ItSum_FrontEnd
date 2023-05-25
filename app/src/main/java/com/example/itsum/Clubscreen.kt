package com.example.itsum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.itsum.databinding.ActivityClubscreenBinding
import com.example.itsum.retrofit.APIService
import com.example.itsum.retrofit.ClubGetData
import kotlinx.android.synthetic.main.activity_clubscreen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Clubscreen : AppCompatActivity() {
  val binding by lazy {ActivityClubscreenBinding.inflate(layoutInflater)}
  val api = APIService.create()
  val id = intent.getIntExtra("id", 0)
  val accessToken = intent.getStringExtra("accessToken")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    clubScreenCloseBtn.setOnClickListener{
      finish()
    }



    api.requestClubData(accessToken, id).enqueue(object :Callback<ClubGetData>{
      override fun onFailure(call: Call<ClubGetData>, t: Throwable) {
        println("log"+t.message)
        val goHomeIntent = Intent(this@Clubscreen, Home::class.java)
        finish()
        startActivity(goHomeIntent)
      }
      override fun onResponse(call: Call<ClubGetData>, response: Response<ClubGetData>) {
        val res = response.body()
        if(res != null) {
          println("log"+res)
          binding.titleView.text = res.title
        }
        else {
          // 존재하지 않는 페이지???
        }
      }
    })

  }


}