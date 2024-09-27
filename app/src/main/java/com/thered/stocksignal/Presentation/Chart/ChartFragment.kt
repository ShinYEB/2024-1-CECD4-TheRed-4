package com.thered.stocksignal.Presentation.Chart

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.thered.stocksignal.R

class ChartFragment : Fragment() {

    companion object {
        fun newInstance() = ChartFragment()
    }

    private val viewModel = ChartViewModel()
    private var dataList = viewModel.loadChartData()

    val entryList = ArrayList<Entry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var view = inflater.inflate(R.layout.fragment_chart, container, false)
        var lineChart = view.findViewById<LineChart>(R.id.line_chart)

        Glide.with(view.context)
            .load("https://previews.123rf.com/images/vectorchef/vectorchef1506/vectorchef150610882/41187816-금융-주식-아이콘.jpg")
            .apply(RequestOptions().transform(RoundedCorners(80)))
            .into(view.findViewById(R.id.imageView))

        dataList.forEachIndexed { index, data ->
            entryList.add(Entry(index.toFloat(), data.toFloat()))
        }

        lineChart.apply {
            // zoom disabled.
            setPinchZoom(true)
            setScaleEnabled(true)
            isDoubleTapToZoomEnabled = true

            // right, left, x axis disabled.
            // legend, description disabled.
            axisRight.isEnabled = false
            axisLeft.isEnabled = false
            xAxis.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = true

            setOnTouchListener { view, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_UP) {
                    highlightValue(null)
                }
                false
            }
        }

        // LineDataSet에 Entry 추가
        val lineDataSet = LineDataSet(entryList, "Price Data")

        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER // 베지어 곡선 모드 설정
        lineDataSet.setDrawCircles(false) // 데이터 포인트에 원 그리지 않음
        lineDataSet.setDrawValues(false)  // 데이터 값을 표시하지 않음
        lineDataSet.lineWidth = 2f        // 선의 두께 설정
        lineDataSet.color = Color.BLUE

        // 최대값과 최소값 구하기
        val maxEntry = entryList.maxByOrNull { it.y }  // 최대값 Entry
        val minEntry = entryList.minByOrNull { it.y }  // 최소값 Entry

        // 최대/최소값만 표시할 데이터셋
        val maxMinEntries = mutableListOf<Entry>()
        maxEntry?.let { maxMinEntries.add(it) }
        minEntry?.let { maxMinEntries.add(it) }

        val maxMinDataSet = LineDataSet(maxMinEntries, "Max/Min Data")
        maxMinDataSet.valueTextSize = 20.0F // 각 지점의 데이터 텍스트 크기
        maxMinDataSet.color = Color.WHITE
        maxMinDataSet.setDrawCircles(false)  // 최대/최소 값에만 원 모양 표시
        maxMinDataSet.setDrawCircleHole(false)
        maxMinDataSet.circleRadius = 5f
        maxMinDataSet.setDrawValues(true)  // 값 표시
        maxMinDataSet.lineWidth = 0f

        // LineData에 두 개의 DataSet 추가
        val lineData = LineData(lineDataSet, maxMinDataSet)

        // LineChart에 데이터 설정
        lineChart.data = lineData
        lineChart.animateX(1500)  // X축 방향으로 애니메이션
        lineChart.description.isEnabled = false  // 차트 설명 비활성화

        // 차트 새로고침
        lineChart.invalidate()

        return view
    }
}