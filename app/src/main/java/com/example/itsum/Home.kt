package com.example.itsum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
//import android.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.appbar.*


class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var navigationView: NavigationView
    lateinit var drawerLayout:DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
            R.id.menu_item1->Toast.makeText(this, "1실행",Toast.LENGTH_SHORT).show()
            R.id.menu_item2->Toast.makeText(this, "2실행",Toast.LENGTH_SHORT).show()
            R.id.menu_item3->Toast.makeText(this, "3실행",Toast.LENGTH_SHORT).show()
        }
        return false
    }
    //액션버튼 클릭 했을 때
    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.icon1 -> {
                //검색 버튼 눌렀을 때
                Toast.makeText(applicationContext, "icon1 눌림", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
            R.id.icon2 -> {
                //공유 버튼 눌렀을 때
                Toast.makeText(applicationContext, "icon2 눌림", Toast.LENGTH_LONG).show()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }*/
}