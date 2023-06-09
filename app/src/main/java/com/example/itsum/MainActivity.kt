package com.example.itsum

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.itsum.databinding.ActivityMainBinding
import com.example.itsum.retrofit.APIService
import com.example.itsum.retrofit.ATM
import com.example.itsum.retrofit.RetrofitConnection
import com.example.itsum.retrofit.kakaoResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
//import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.*
import com.kakao.sdk.common.model.AuthErrorCause.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    val api = APIService.create()
    val atm = ATM
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var startGoogleLoginForResult : ActivityResultLauncher<Intent>
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

        try {
            val account = task.getResult(ApiException::class.java)
            // 이름, 이메일 등이 필요하다면 아래와 같이 account를 통해 각 메소드를 불러올 수 있다.
            val userName = account.givenName
            val serverAuth = account.serverAuthCode
            println("로그인 성공")
            println("이름 : " + userName)
            println("serverAuth : " + serverAuth)

        } catch (e: ApiException) {
            println("로그인 실패"+e)
        }


    }
    private val RC_SIGN_IN = 116
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
            val intent = Intent(this, Home::class.java)
            finish()
            startActivity(intent)
        }
        binding.joinBtn.setOnClickListener {   //회원가입 눌렀을 때
            kakaoLogout()
            googleLogout()
        }
        binding.googleLogin.setOnClickListener{
            googleSignInClient.signOut()
            val signInIntent = googleSignInClient.signInIntent
            googleAuthLauncher.launch(signInIntent)
        }
        binding.kakaoLogin.setOnClickListener {
            kakaoLogin()
        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // 서버 통신 확인
    private fun ret(token: OAuthToken){

        api.kakaoLoginAuth(token.accessToken).enqueue(object : Callback<kakaoResponse>{
            override fun onFailure(call: Call<kakaoResponse>, t: Throwable) {
                //TextMsg(this@MainActivity, "retrofit 실패 \n\n")
                println("실패")
                Log.d("백엔드","메세지 + " +  t.message)
            }

            override fun onResponse(
                call: Call<kakaoResponse>,
                response: Response<kakaoResponse>
            ) {
                println("response = " + response.body())
                println("oauth response = " + token.accessToken)
                println("성공")
                ATM.setToken(response.body()!!.appToken)
                ATM.setId(response.body()!!.socialId)
                ATM.setName(response.body()!!.userName)

            }
        })
    }
    private fun kakaoLogin() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        println("출력")

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
                                "me: ${token.idToken}"
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
                    ret(token)
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
                TextMsg(this, "로그아웃 실패. Error : ${error}")
            }
            else {
                TextMsg(this, "로그아웃 성공. SDK에서 토큰 삭제됨")
                setLogin(false)
            }
        }
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(getString(R.string.google_client_id)) // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청한다.
            .requestEmail() // 이메일도 요청할 수 있다.
            .build()

        return GoogleSignIn.getClient(this, googleSignInOption)
    }
    private fun googleLogout(){
        googleSignInClient?.signOut()
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