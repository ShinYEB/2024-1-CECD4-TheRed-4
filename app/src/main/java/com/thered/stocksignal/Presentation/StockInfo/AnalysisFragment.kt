package com.thered.stocksignal.presentation.stockinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.SeekBar
import android.widget.TextView
import com.thered.stocksignal.R

class AnalysisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // fragment_analysis.xml을 연결
        return inflater.inflate(R.layout.fragment_analysis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UI 요소들 연결
        val startPriceTextView: TextView = view.findViewById(R.id.start_price)
        val endPriceTextView: TextView = view.findViewById(R.id.end_price)
        val volumeTextView: TextView = view.findViewById(R.id.volume)
        val tradeAmountTextView: TextView = view.findViewById(R.id.trade_amount)

        // SeekBar들
        val dayseekbar: SeekBar = view.findViewById(R.id.day_seekbar)
        val year_seekbar: SeekBar = view.findViewById(R.id.year_seekbar)

        // 필요하다면 여기서 데이터를 동적으로 업데이트할 수 있음
        startPriceTextView.text = "84,700원"
        endPriceTextView.text = "84,400원"
        volumeTextView.text = "9,212,890주"
        tradeAmountTextView.text = "7,793억 원"

        // SeekBar 초기값 설정 (현재는 임의의 값)
        dayseekbar.progress = 84
        year_seekbar.progress = 85
    }
}
