package com.example.itsum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import com.kakao.sdk.common.util.Utility
import android.widget.Button
import com.example.itsum.retrofit.RetrofitConnection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //해시키 구하기
        //val keyHash = Utility.getKeyHash(this)
        //Log.d("Hash", keyHash)

        login_btn.setOnClickListener {  //로그인버튼 눌렀을 때
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        join_btn.setOnClickListener {   //회원가입 눌렀을 때
            val intent = Intent(this, NewJoin::class.java)
            startActivity(intent)
        }
        kakao_login.setOnClickListener {
            val retro = RetrofitConnection.getInstance()
        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


}