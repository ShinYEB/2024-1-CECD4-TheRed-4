package com.thered.stocksignal.Presentation.StockInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.thered.stocksignal.R

class NewsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뉴스 데이터 설정 (예시)
        val articlePreview: TextView = view.findViewById(R.id.articlePreview)
        articlePreview.text = """
            삼성전자가 AI 기술을 통해 주식 시장에서 놀라운 성과를 거두었습니다. 
            최근 발표된 실적에 따르면 매출이 20% 증가했으며, 이는 시장의 예측을 뛰어넘는 결과입니다.
        """.trimIndent()
    }
}
