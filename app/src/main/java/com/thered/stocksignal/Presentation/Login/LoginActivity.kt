package com.thered.stocksignal.Presentation.Login
import android.net.Uri
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.thered.stocksignal.Presentation.Main.MainActivity
import com.thered.stocksignal.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.POST
import com.thered.stocksignal.Data.model.TokenResponse
import com.thered.stocksignal.Data.Network.LoginApiService
import com.thered.stocksignal.Data.Network.LoginRequest


class LoginActivity : AppCompatActivity() {

    private val loginButton: ImageButton by lazy { findViewById(R.id.kakao_login_button) }
    private lateinit var loginLauncher: ActivityResultLauncher<Intent>
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    // 로그인 콜백 정의
//    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
//        if (error != null) {
//            handleError(error) // 카카오 계정으로 로그인 실패
//        } else if (token != null) {
//            Toast.makeText(this, "Login successful.", Toast.LENGTH_SHORT).show() // 카카오 로그인 성공
//
//            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//                if (error != null) {
//                    Log.d("KakaoLogin", "Token information verification failed: ${error.message}") // 카카오 로그인 토큰 정보 확인 실패
//                    Toast.makeText(this, "Token information verification failed", Toast.LENGTH_SHORT).show()
//                } else if (tokenInfo != null) {
//                    Log.d("KakaoLogin", "Token information verification successful") //토큰 정보 확인 성공
//                    // 로그인 성공 시, MainActivity로 이동. 로그인 성공 이후에는 다시 로그인 화면으로 돌아오지 않게 하는 코드
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                    finish()
//                }
//            }
//        }
//    }
    // 카카오 로그인 구현
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Kakao SDK 초기화
        // KakaoSdk.init(this, "c93bfd494630d7b92e90714929d816e3")
        // 해시 키 가져오기
        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)

        // 인텐트 데이터 처리를 위한 메서드 호출

//        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            Log.d("LoginActivity", "result")
//
//            if (result.resultCode == RESULT_OK) {
//                // Intent 값을 받아서 handleIntent() 실행
//                val returnedIntent = result.data
//                if (returnedIntent != null) {
//                    handleIntent(returnedIntent)
//                } else {
//                    Log.e("LoginActivity", "Returned Intent is null")
//                }
//            } else {
//                Log.e("LoginActivity", "Activity result is not OK")
//            }
//        }

        loginButton.setOnClickListener {
            Log.d("KakaoLogin", "Click the login button")
//            val clientId = "859897bc7ad1eed0ca3e9bac1b83bb68"
//            val redirectUri = "http://10.0.2.16:3000/api/auth/kakao/callback"
//
//            // 인가 코드 요청 URL 생성
//            val authUrl = "https://kauth.kakao.com/oauth/authorize?client_id=$clientId&redirect_uri=$redirectUri&response_type=code"
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(authUrl))

//            activityResultLauncher.launch(intent)




            UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                Log.d("KakaoLogin", "실패?")
                if (error != null) {
                    Log.d("KakaoLogin", "로그인 실패", error)
                }
                else if (token != null) {
                    Log.d("KakaoLogin", "로그인 성공 ${token.accessToken}")
                }
                else {
                    Log.d("KakaoLogin", "실패")
                }
            }
//            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
//                if (error != null) {
//                    Log.d("KakaoLogin", "카카오계정으로 로그인 실패", error)
//                } else if (token != null) {
//                    Log.d("KakaoLogin", "카카오계정으로 로그인 성공 ${token.accessToken}")
//                }
//            }
//
//// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
//            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
//                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
//                    if (error != null) {
//                        Log.d("KakaoLogin", "카카오톡으로 로그인 실패", error)
//
//                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
//                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
////                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
////                            return@loginWithKakaoTalk
////                        }
//
//                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
//                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
//                    } else if (token != null) {
//                        Log.d("KakaoLogin", "카카오톡으로 로그인 성공 ${token.accessToken}")
//                    }
//                }
//            } else {
//                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
//            }

        }
    }

    // 인텐트 데이터 처리
//    private fun handleIntent(intent: Intent?) {
//        intent?.data?.let { uri ->
//            Log.d("KakaoLogin", "Attempting to process authorization code in onCreate with URI: $uri")
//            handleRedirect(uri) // 인가 코드를 처리하는 메서드 호출
//        } ?: run {
//            Log.d("KakaoLogin", "Intent data is null") // 인텐트 데이터가 없을 경우 로그
//        }
//    }


    private fun handleRedirect(uri: Uri?) {
        // 호출된 URI 로그 출력
        Log.d("KakaoLogin", "handleRedirect called with URI: $uri")
        if (uri == null) {
            Log.d("KakaoAuthCode", "URI is null.")
            return
        }
        // URI에서 인가 코드 추출
        val code = uri.getQueryParameter("code")
        Log.d("KakaoAuthCode", "Extracted authorization code: $code")

        // 인가 코드가 존재한다면
        if (code != null) {
            Log.d("KakaoAuthCode", "Authorization code: $code")

            // Retrofit 설정
            val retrofit = Retrofit.Builder()
                .baseUrl("http://pposiraun.com/") // 백엔드 URL
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val apiService = retrofit.create(LoginApiService::class.java)

            // 인가 코드를 백엔드로 전송
            Log.d("KakaoAuthCode", "Sending code to backend: $code")

            // 인가 코드를 LoginRequest 객체로 감싸서 전달
            val loginRequest = LoginRequest(code)

            // 요청 로그 추가
            Log.d("KakaoAuthCode", "LoginRequest: $loginRequest")
            apiService.loginWithKakao(loginRequest).enqueue(object : Callback<TokenResponse> {
                override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                    Log.d("KakaoAuthCode", "Response received from backend.")
                    if (response.isSuccessful) {
                        Log.d("KakaoAuthCode", "Response is successful.")
                        val tokenResponse = response.body()
                        tokenResponse?.let {
                            val accessToken = it.data?.token
                            val userId = it.data?.userId
                            Log.d("AccessToken", "Access token: $accessToken")
                            Log.d("UserId", "User ID: $userId")
                            // 로그인 성공 시 MainActivity로 이동
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            finish()
                        } ?: run {
                            Log.d("KakaoAuthCode", "TokenResponse body is null.")
                        }
                    } else {
                        Log.d("KakaoAuthCode", "Response code: ${response.code()}")
                        Log.d("KakaoAuthCode", "Response message: ${response.message()}")
                        Log.d("Error", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.d("Error", "Network Error: ${t.message}")
                    Log.d("KakaoAuthCode", "Failed to send request to backend.")
                }
            })
        } else {
            Log.d("KakaoAuthCode", "Authorization code not present.")
        }
    }


    // 에러 메세지
    private fun handleError(error: Throwable) {
        when {
            error.toString() == com.kakao.sdk.common.model.AuthErrorCause.AccessDenied.toString() -> {
                Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
            }
            error.toString() == com.kakao.sdk.common.model.AuthErrorCause.InvalidClient.toString() -> {
                Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
            }
            error.toString() == com.kakao.sdk.common.model.AuthErrorCause.InvalidGrant.toString() -> {
                Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
            }
            error.toString() == com.kakao.sdk.common.model.AuthErrorCause.InvalidRequest.toString() -> {
                Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
            }
            error.toString() == com.kakao.sdk.common.model.AuthErrorCause.InvalidScope.toString() -> {
                Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
            }
            error.toString() == com.kakao.sdk.common.model.AuthErrorCause.Misconfigured.toString() -> {
                Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
            }
            error.toString() == com.kakao.sdk.common.model.AuthErrorCause.ServerError.toString() -> {
                Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
            }
            error.toString() == com.kakao.sdk.common.model.AuthErrorCause.Unauthorized.toString() -> {
                Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
            }
            else -> { // Unknown
                Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
