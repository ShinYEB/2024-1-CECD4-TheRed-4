package com.thered.stocksignal.presentation

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.ImageView
import com.thered.stocksignal.R
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.thered.stocksignal.presentation.stockinfo.AnalysisFragment
import com.thered.stocksignal.presentation.stockinfo.AutoTradeFragment
import com.thered.stocksignal.presentation.stockinfo.PredictionFragment
import com.thered.stocksignal.presentation.stockinfo.NewsFragment

class StockInfoActivity : AppCompatActivity() {
    private lateinit var articlePreview: TextView
    private lateinit var predictionImage: ImageView
    private lateinit var newsButton: Button
    private lateinit var analysisButton: Button
    private lateinit var aiButton: Button
    private lateinit var autoTradeButton: Button  // 자동매매 버튼 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_info)

        // UI 요소 초기화
        newsButton = findViewById(R.id.button1)
        analysisButton = findViewById(R.id.button2)
        aiButton = findViewById(R.id.button3)
        autoTradeButton = findViewById(R.id.button4)  // 자동매매 버튼 초기화

        // 기본 뉴스 Fragment 로드
        if (savedInstanceState == null) {
            switchFragment(NewsFragment()) // 뉴스 Fragment를 기본으로 로드
            newsButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0080FF")) // 뉴스 버튼 색상 변경
        }

        // 뉴스 버튼 클릭 리스너
        newsButton.setOnClickListener {
            switchFragment(NewsFragment())
            resetButtonColors()
            newsButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0080FF")) // 배경 색상 변경
        }

        // AI 예측 버튼 클릭 리스너
        aiButton.setOnClickListener {
            // AI 예측 Fragment로 전환
            switchFragment(PredictionFragment())
            resetButtonColors()
            aiButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0080FF")) // 배경 색상 변경
        }

        // 분석 버튼 클릭 리스너
        analysisButton.setOnClickListener {
            switchFragment(AnalysisFragment())
            resetButtonColors()
            analysisButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0080FF")) // 배경 색상 변경
        }

        // 자동매매 버튼 클릭 리스너 추가
        autoTradeButton.setOnClickListener {
            switchFragment(AutoTradeFragment()) // 자동매매 Fragment로 전환
            resetButtonColors()
            autoTradeButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#0080FF")) // 배경 색상 변경
        }
    }

    // Fragment 전환 함수
    private fun switchFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null) // 백스택에 추가 (뒤로가기 가능)
        fragmentTransaction.commit()
    }

    // 버튼 색상 초기화 함수
    private fun resetButtonColors() {
        // 버튼 색상 초기화
        newsButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D3D3D3")) // 기본 색상으로 변경
        aiButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D3D3D3")) // 기본 색상으로 변경
        analysisButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D3D3D3")) // 기본 색상으로 변경
        autoTradeButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D3D3D3")) // 기본 색상으로 변경
    }
}
