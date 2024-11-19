package com.thered.stocksignal.Presentation.StockInfo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thered.stocksignal.Data.Network.ProfitRateListener
import com.thered.stocksignal.Data.Network.ScenarioApiService
import com.thered.stocksignal.Data.model.ScenarioData
import com.thered.stocksignal.Presentation.NewScenario.NewScenarioActivity
import com.thered.stocksignal.R
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AutoTradeFragment : Fragment(), ProfitRateListener {  // ProfitRateListener 구현

    private lateinit var conditionAdapter: ConditionAdapter
    private val conditionList = mutableListOf<com.thered.stocksignal.Data.model.Condition>() // 타입 수정
    private val token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsIm5pY2tuYW1lIjoi7ZmN7JuQ7KSAIiwiaWF0IjoxNzMxMDU0NzczLCJleHAiOjE3MzQ2NTQ3NzN9.gWoR45M4tTpwx1gyk8oiZqUQfvw3aHuaqDxXdKqilDs" // 실제 토큰으로 변경
    private var profitRateListener: ProfitRateListener? = null

    private val scenarioApiService: ScenarioApiService = Retrofit.Builder()
        .baseUrl("https://pposiraun.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ScenarioApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auto_trade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        val recyclerView: RecyclerView = view.findViewById(R.id.conditions_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        conditionAdapter = ConditionAdapter(conditionList)
        recyclerView.adapter = conditionAdapter

        // '+ 버튼' 클릭 리스너 설정
        val addButton: Button = view.findViewById(R.id.add_condition)// 해당 버튼의 ID
        addButton.setOnClickListener {
            // NewScenarioActivity로 전환
            val intent = Intent(activity, NewScenarioActivity::class.java)
            startActivity(intent)
        }
        // 데이터를 API에서 받아오기
        getScenarioData()
    }

    private fun getScenarioData() {
        lifecycleScope.launch {
            try {
                val response = scenarioApiService.getScenarios("Bearer $token")
                Log.d("AutoTradeFragment", "Response code: ${response.code()}")
                Log.d("AutoTradeFragment", "Response body: ${response.body()}")  // 응답 본문 출력


                if (response.isSuccessful) {
                    val apiResponse = response.body() // ApiResponse로 변환된 응답

                    // response.body()가 null일 경우를 안전하게 처리
                    if (apiResponse != null) {
                        val scenarioDataList = apiResponse.data ?: emptyList()

                        Log.d("AutoTradeFragment", "Received scenario data: $scenarioDataList")  // 받아온 데이터 로그 찍기

                        if (scenarioDataList.isNullOrEmpty()) {
                            // 데이터가 없을 때 처리 (예: "데이터가 없습니다" 메시지 표시)
                            showEmptyDataMessage()
                        } else {
                            conditionList.clear()
                            scenarioDataList.forEach { scenarioData ->
                                conditionList.add(
                                    com.thered.stocksignal.Data.model.Condition( // 타입 일치
                                        name = scenarioData.scenarioName,
                                        profitRate = calculateProfitRate(scenarioData.initialPrice, scenarioData.currentPrice)
                                    )
                                )
                            }
                            conditionAdapter.notifyDataSetChanged()
                        }
                    } else {
                        Log.d("AutoTradeFragment", "API response body is null")
                        showErrorMessage("API 응답이 없습니다.")
                    }
                } else {
                    Log.d("AutoTradeFragment", "Failed to load data, Response code: ${response.code()}")
                    showErrorMessage("API 요청 실패")
                }
            } catch (e: Exception) {
                Log.e("AutoTradeFragment", "Error fetching data: ${e.message}")
                showErrorMessage("네트워크 오류: ${e.message}")
            }
        }
    }

    private fun showEmptyDataMessage() {
        // 데이터 없음
        Log.d("AutoTradeFragment", "No data available")
    }

    private fun showErrorMessage(message: String) {
        Log.d("AutoTradeFragment", "Error: $message")
    }

    private fun calculateProfitRate(initialPrice: Double, currentPrice: Double): String {
        if (initialPrice == 0.0) return "N/A"
        val profit = (currentPrice - initialPrice) / initialPrice * 100
        val profitRate = String.format("+%.2f%%", profit)

        Log.d("AutoTradeFragment", "Calculated Profit Rate: $profitRate")
        // ProfitRateListener를 통해 수익률을 전달
        profitRateListener?.onProfitRateCalculated(profitRate)
        return profitRate
    }

    override fun onProfitRateCalculated(profitRate: String) {
        // 수익률 계산 후 할 작업을 구현
        Log.d("AutoTradeFragment", "Calculated profit rate: $profitRate")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ProfitRateListener) {
            profitRateListener = context
        } else {
            // ProfitRateListener를 구현하지 않은 경우 처리 (필요시 예외 처리)
        }
    }

    override fun onDetach() {
        super.onDetach()
        profitRateListener = null
    }
}
