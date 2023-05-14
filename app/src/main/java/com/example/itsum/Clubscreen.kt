package com.example.itsum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.example.itsum.databinding.ActivityClubscreenBinding
import com.example.itsum.retrofit.APIService
import com.example.itsum.retrofit.ClubGetData
import kotlinx.android.synthetic.main.activity_clubscreen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Clubscreen : AppCompatActivity() {
  val binding by lazy {ActivityClubscreenBinding.inflate(layoutInflater)}
  val api = APIService.create()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_clubscreen)

    clubScreenCloseBtn.setOnClickListener{
      finish()
    }

    api.requestClubData().enqueue(object :Callback<ClubGetData>{
      override fun onResponse(call: Call<ClubGetData>, response: Response<ClubGetData>) {
        Log.d("log",response.toString())
      }

      override fun onFailure(call: Call<ClubGetData>, t: Throwable) {
        Log.d("log",t.message.toString())
      }
    })

  }


}