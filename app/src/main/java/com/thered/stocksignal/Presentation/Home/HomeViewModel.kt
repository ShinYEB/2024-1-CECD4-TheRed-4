package com.thered.stocksignal.Presentation.Home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private var tempData =  listOf(listOf("삼성전자", "65,200", "+0.93%", ""),
                            listOf("SK하이닉스", "188,300", "+4.04%", ""),
                            listOf("현대차", "255,500", "-1.16%", ""),
                            listOf("롯데칠성", "133,600", "-0.67%", ""),
                            listOf("KB금융", "84,300", "+3.82%", ""),
                            listOf("넥슨게임즈", "15,440", "-0.39%", ""),
                            listOf("카카오", "36,600", "-0.27%", ""),
                            listOf("NAVER", "169,500", "-0.82%", ""),
                            listOf("하이브", "170,800", "+0.59%", ""),
                            listOf("에스엠", "68,500", "+3.48%", "")
    )

    fun loadData(): List<List<String>> {
        return tempData
    }
}