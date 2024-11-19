package com.thered.stocksignal.Presentation

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.thered.stocksignal.Data.Network.CompanyApiService
import com.thered.stocksignal.Data.model.CompanyResponse
import com.thered.stocksignal.Presentation.StockInfo.AnalysisFragment
import com.thered.stocksignal.Presentation.StockInfo.AutoTradeFragment
import com.thered.stocksignal.Presentation.StockInfo.PredictionFragment
import com.thered.stocksignal.Presentation.StockInfo.NewsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.thered.stocksignal.R
import androidx.fragment.app.DialogFragment

class StockInfoActivity : AppCompatActivity() {
    private lateinit var articlePreview: TextView
    private lateinit var predictionImage: ImageView
    private lateinit var newsButton: Button
    private lateinit var analysisButton: Button
    private lateinit var aiButton: Button
    private lateinit var autoTradeButton: Button
    private lateinit var companyNameTextView: TextView
    private lateinit var sellNowButton: Button
    private lateinit var apiService: CompanyApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_info)

        // UI 요소 초기화
        newsButton = findViewById(R.id.button1)
        analysisButton = findViewById(R.id.button2)
        aiButton = findViewById(R.id.button3)
        autoTradeButton = findViewById(R.id.button4)
        companyNameTextView = findViewById(R.id.company_name_text_view)
        sellNowButton = findViewById(R.id.sellButton)

        // 즉시 거래하기 버튼 클릭 리스너 추가
        sellNowButton.setOnClickListener {
            showsellDialog() // 버튼 클릭 시 다이얼로그 표시
        }

        // Retrofit 초기화
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pposiraun.com/") // 실제 API 엔드포인트 URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(CompanyApiService::class.java)

        // 기본 분석 Fragment 로드
        if (savedInstanceState == null) {
            switchFragment(AnalysisFragment())
            analysisButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0080FF"))
            analysisButton.setTextColor(Color.WHITE)
        }

        // 버튼 클릭 리스너 설정
        newsButton.setOnClickListener {
            switchFragment(NewsFragment())
            resetButtonColors()
            newsButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0080FF"))
            newsButton.setTextColor(Color.WHITE)
        }

        aiButton.setOnClickListener {
            switchFragment(PredictionFragment())
            resetButtonColors()
            aiButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0080FF"))
            aiButton.setTextColor(Color.WHITE)
        }

        analysisButton.setOnClickListener {
            switchFragment(AnalysisFragment())
            resetButtonColors()
            analysisButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0080FF"))
            analysisButton.setTextColor(Color.WHITE)
        }

        autoTradeButton.setOnClickListener {
            switchFragment(AutoTradeFragment())
            resetButtonColors()
            autoTradeButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0080FF"))
            autoTradeButton.setTextColor(Color.WHITE)
        }

        // 회사 정보 및 로고 가져오기
        getCompanyInfo("삼성전자")
        getCompanyLogo("삼성전자")
    }

    private fun getCompanyInfo(companyName: String) {
        apiService.getCompanyInfo(companyName).enqueue(object : Callback<CompanyResponse> {
            override fun onResponse(call: Call<CompanyResponse>, response: Response<CompanyResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val companyCode = response.body()?.data?.companyCode ?: "정보 없음"
                    companyNameTextView.text = companyCode
                } else {
                    companyNameTextView.text = "정보를 가져오는 데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<CompanyResponse>, t: Throwable) {
                companyNameTextView.text = "오류 발생: ${t.message}"
            }
        })
    }

    private fun getCompanyLogo(companyName: String) {
        apiService.getCompanyLogo(companyName).enqueue(object : Callback<CompanyResponse> {
            override fun onResponse(call: Call<CompanyResponse>, response: Response<CompanyResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    // logoImage ID를 가져와서 Google Drive URL로 변환
                    val logoImageId = response.body()?.data?.logoImage ?: ""
                    val logoUrl = "https://drive.google.com/thumbnail?id=$logoImageId"

                    val companyLogoImageView: ImageView = findViewById(R.id.companyLogo)

                    Glide.with(this@StockInfoActivity)
                        .load(logoUrl)
                        .placeholder(R.drawable.logo_title)
                        .error(R.drawable.chatbot)
                        .into(companyLogoImageView)
                } else {
                    companyNameTextView.text = "회사 로고를 가져오는 데 실패했습니다."
                }
            }

            override fun onFailure(call: Call<CompanyResponse>, t: Throwable) {
                companyNameTextView.text = "오류 발생: ${t.message}"
            }
        })
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    class SellNowDialogFragment : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return super.onCreateDialog(savedInstanceState)
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.dialog_buy_now, container, false)
            val confirmButton = view.findViewById<Button>(R.id.buy_button)
            val cancelButton = view.findViewById<Button>(R.id.close_button)

            confirmButton.setOnClickListener {
                dismiss()
            }

            cancelButton.setOnClickListener {
                dismiss()
            }

            return view
        }

        override fun onStart() {
            super.onStart()
            dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    private fun showsellDialog() {
        val dialog = SellNowDialogFragment()
        dialog.show(supportFragmentManager, "SellNowDialog")
    }

    private fun resetButtonColors() {
        newsButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
        newsButton.setTextColor(Color.BLACK)
        aiButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
        aiButton.setTextColor(Color.BLACK)
        analysisButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
        analysisButton.setTextColor(Color.BLACK)
        autoTradeButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
        autoTradeButton.setTextColor(Color.BLACK)
    }

    override fun onBackPressed() {
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
