package com.example.itsum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View.inflate
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
//import android.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.itsum.databinding.ActivityHomeBinding
import com.example.itsum.retrofit.*
import com.example.itsum.retrofit.APIService
import com.example.itsum.retrofit.ClubPostData
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.appbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout:DrawerLayout
    private lateinit var binding:ActivityHomeBinding
    private val api = APIService.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar1)
        setSupportActionBar(toolbar1)
        //Toolbar에 표시되는 제목의 표시 유무를 설정. false로 해야 custom한 툴바의 이름이 화면에 보인다.
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //왼쪽 버튼 사용설정(기본은 뒤로가기)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //왼쪽 버튼 아이콘 변경
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

        //네비게이션 드로어
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        //리사이클러뷰
        val list = ArrayList<ClubPostData>()
        list.add(ClubPostData("코딩스터디_파이썬", "1", "name 1", 1,"python","direct", "member"))
        list.add(ClubPostData("코딩스터디_자바", "2", "name 2", 1,"java","direct","member"))
        list.add(ClubPostData("코딩스터디_C++", "3", "name 3", 1,"C++","direct","member"))

        api.searchUsingGet().enqueue(object :Callback<ClubSearchResponse>{
            override fun onResponse(call: Call<ClubSearchResponse>, response: Response<ClubSearchResponse>) {
                Log.d("성공",response.toString())
                println("성공"+response.body())
            }

            override fun onFailure(call: Call<ClubSearchResponse>, t: Throwable) {
                Log.d("실패",t.message.toString())
            }
        })
        val adapter = RecyclerUserAdapter(list)
        lstUser.adapter = adapter

        //다이얼로그
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener(){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener{
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("필터")

            val  mAlertDialog = mBuilder.show()

            val okButton = mDialogView.findViewById<Button>(R.id.search_btn)
            okButton.setOnClickListener {

            }

            val noButton = mDialogView.findViewById<Button>(R.id.closeButton)
            noButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
    }
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.templaye_toolbar_menu, menu)
        return true
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            android.R.id.home->{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_item1 -> {
                //모집만들기 눌렀을 때
                val makeClubIntent = Intent(this, MakeClub::class.java)
                val accessToken = intent.getStringExtra("accessToken")
                makeClubIntent.putExtra("accessToken",accessToken)
                startActivity(makeClubIntent)
                return super.onOptionsItemSelected(item)
            }
            R.id.menu_item2 -> {
                //마이페이지 눌렀을 때
                val myPageIntent = Intent(this, MyPage::class.java)
                startActivity(myPageIntent)
                return super.onOptionsItemSelected(item)
            }
            R.id.menu_item3 ->{
                //최근 모집(추가예정)
            }
        }
        return false
    }
}