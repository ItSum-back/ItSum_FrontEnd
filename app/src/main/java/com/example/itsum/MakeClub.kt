package com.example.itsum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.itsum.databinding.ActivityMakeClubBinding
import com.example.itsum.retrofit.APIService
import com.example.itsum.retrofit.ATM
import com.example.itsum.retrofit.ClubPostData
import com.example.itsum.retrofit.ClubPostResponse
import kotlinx.android.synthetic.main.activity_make_club.*
import retrofit2.*

class MakeClub : AppCompatActivity() {
  private var _binding: ActivityMakeClubBinding? = null
  private val binding get() = _binding!!
  val api = APIService.create()
  val at = ATM
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMakeClubBinding.inflate(layoutInflater)
    setContentView(binding.root)

    makeClubCloseBtn.setOnClickListener{
      finish()
    }

    val recruitClassSpinner = findViewById<Spinner>(R.id.recruitClassSpinner)
    val recruitClassList = listOf("프로젝트","스터디")
    val recruitClassAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recruitClassList)

    recruitClassSpinner.adapter = recruitClassAdapter
    recruitClassSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
      }
      override fun onNothingSelected(p0: AdapterView<*>?) {
      }
    }

    val onOfflineSpinner = findViewById<Spinner>(R.id.onOffline)
    val onOfflineList = listOf("온라인","오프라인")
    val onOfflineAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, onOfflineList)
    onOfflineSpinner.adapter = onOfflineAdapter
    onOfflineSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
      }
      override fun onNothingSelected(p0: AdapterView<*>?) {
      }
    }

    val title = binding.title
    val contents = binding.contents
    // val personnel = binding.personnel.text.toString().trim()
    val meetingWays = binding.meetingWays
    val category = binding.recruitClassSpinner
    val recruitedMember = binding.recruitedMember.text.toString()
    val startDate = findViewById<EditText>(R.id.startDate)
    val expectedPeriod = findViewById<EditText>(R.id.expectedPeriod)

    // 만약 새로 만들기로 들어온다 -> 데이터 없이 페이지 렌더링
    val accessToken = at.getToken()
    binding.createClubBtn.setOnClickListener{
      val title = title.text.toString()
      val contents = contents.text.toString()
      val positionList = "백엔드, 프론트엔드, 아직 못정함"
      val personnel : Int = binding.personnel.text.toString().trim().toInt()
      val techSkill = "자바, 코틀린, aws"
      val meetingWays = meetingWays.text.toString()
      val members = "의미 불명???"
      val category = category.selectedItem.toString()
      val data = ClubPostData(title,contents,positionList,personnel,techSkill,meetingWays,members,category)
      api.requestClubPost("Bearer ${accessToken}" ,data).enqueue(object : Callback<ClubPostResponse> {
        override fun onFailure(call: Call<ClubPostResponse>, t: Throwable) {
          val goHomeIntent = Intent(this@MakeClub, Home::class.java)
          finish()
          startActivity(goHomeIntent)
        }

        override fun onResponse(call: Call<ClubPostResponse>, response: Response<ClubPostResponse>) {
          val clubScreenIntent = Intent(this@MakeClub, Clubscreen::class.java)
          clubScreenIntent.putExtra("id", response.body()?.data)
          finish()
          startActivity(clubScreenIntent)
        }

      })

      // 만약 수정하기로 들어온다 -> 서버에서 데이터 받아온 후 페이지 렌더링 및 데이터 뿌림
    }
  }
}