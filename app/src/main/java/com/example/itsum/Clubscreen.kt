package com.example.itsum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.itsum.retrofit.APIService
import kotlinx.android.synthetic.main.activity_clubscreen.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Clubscreen : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_clubscreen)

    clubScreenCloseBtn.setOnClickListener{
      finish()
    }

    var recruitMemberCount = findViewById<TextView>(R.id.recruitMemberCount)

    var retrofit = Retrofit.Builder()
      .baseUrl("https://localhost:8080")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
    var ClubGetService:APIService = retrofit.create(APIService::class.java)


  }


}