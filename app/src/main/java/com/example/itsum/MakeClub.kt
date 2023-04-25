package com.example.itsum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.itsum.databinding.ActivityMakeClubBinding

class MakeClub : AppCompatActivity() {
  private lateinit var binding: ActivityMakeClubBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_make_club)
    binding = ActivityMakeClubBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    var recruitClassSpinner = findViewById<Spinner>(R.id.recruitClassSpinner)
    var recruitClassList = listOf("프로젝트","스터디")
    var recruitClassAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recruitClassList)

    recruitClassSpinner.adapter = recruitClassAdapter
    recruitClassSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
      }
      override fun onNothingSelected(p0: AdapterView<*>?) {
      }
    }

    var onOfflineSpinner = findViewById<Spinner>(R.id.onOffline)
    var onOfflineList = listOf("온라인","오프라인")
    var onOfflineAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, onOfflineList)
    onOfflineSpinner.adapter = onOfflineAdapter
    onOfflineSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
      }
      override fun onNothingSelected(p0: AdapterView<*>?) {
      }
    }

    var recruitMemberCount = findViewById<EditText>(R.id.recruitMemberCount)
    var recruitedMember = findViewById<EditText>(R.id.recruitedMember)
    var startDate = findViewById<EditText>(R.id.startDate)
    var expectedPeriod = findViewById<EditText>(R.id.expectedPeriod)
    var contactMethod = findViewById<EditText>(R.id.contactMethod)
    var recruitTitle = findViewById<EditText>(R.id.recruitTitle)
    var recruitExplain = findViewById<EditText>(R.id.recruitExplain)

    // 만약 새로 만들기로 들어온다 -> 데이터 없이 페이지 렌더링
    // 만약 수정하기로 들어온다 -> 서버에서 데이터 받아온 후 페이지 렌더링 및 데이터 뿌림
  }
}