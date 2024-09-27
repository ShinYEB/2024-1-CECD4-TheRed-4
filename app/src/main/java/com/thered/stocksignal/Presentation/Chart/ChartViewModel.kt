package com.thered.stocksignal.Presentation.Chart

import androidx.lifecycle.ViewModel

class ChartViewModel : ViewModel() {
    private var dataPoints = listOf(300, 200, 700, 300, 110, 250, 70, 400, 300, 200, 400, 300, 110, 250, 70, 25, 400, 300, 200, 400, 300, 110, 250, 70, 400, 300, 200, 400, 300, 110, 250, 70, 400)

    fun loadChartData(): List<Int> {
        return dataPoints
    }

}