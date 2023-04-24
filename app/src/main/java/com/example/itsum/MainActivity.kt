package com.example.itsum

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import com.kakao.sdk.common.util.Utility
import android.widget.Button
import com.example.itsum.databinding.ActivityMainBinding
import com.example.itsum.retrofit.RetrofitConnection
//import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.*
import com.kakao.sdk.common.model.AuthErrorCause.*

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        //해시키 구하기
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)

        binding.loginBtn.setOnClickListener {  //로그인버튼 눌렀을 때
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        binding.joinBtn.setOnClickListener {   //회원가입 눌렀을 때
            //val intent = Intent(this, NewJoin::class.java)
            //startActivity(intent)
            kakaoLogout()

        }
        binding.kakaoLogin.setOnClickListener {
            //val retro = RetrofitConnection.getInstance()
            kakaoLogin()

        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun kakaoLogin() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d("tag", "카카오계정으로 로그인 실패 : ${error}")
                setLogin(false)
            } else if (token != null) {
                //TODO: 최종적으로 카카오로그인 및 유저정보 가져온 결과
                UserApiClient.instance.me { user, error ->
                    TextMsg(
                        this, "카카오계정으로 로그인 성공 \n\n " +
                                "token: ${token.accessToken} \n\n " +
                                "me: ${user}"
                    )
                    setLogin(true)
                    Log.d("tag", "token: ${token.accessToken}")
                }
            }

        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    TextMsg(this, "카카오톡으로 로그인 실패 : ${error}")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    TextMsg(this, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    setLogin(true)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }

    }
    fun kakaoLogout(){
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                TextMsg(this, "로그아웃 실패. SDK에서 토큰 삭제됨: ${error}")
            }
            else {
                TextMsg(this, "로그아웃 성공. SDK에서 토큰 삭제됨")
                setLogin(false)
            }
        }
    }
    private fun TextMsg(act: Activity, msg : String){
        binding.textView2.text = msg
    }

    private fun setLogin(bool: Boolean){
        //binding.textView2.visibility = if(bool) View.GONE else View.VISIBLE
        //binding.btnStartKakaoLogout.visibility = if(bool) View.VISIBLE else View.GONE
        //binding.btnStartKakaoUnlink.visibility = if(bool) View.VISIBLE else View.GONE
    }

}