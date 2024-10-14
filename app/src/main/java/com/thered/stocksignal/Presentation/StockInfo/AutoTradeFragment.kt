package com.thered.stocksignal.presentation.stockinfo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thered.stocksignal.presentation.newScenario.NewScenarioConditionActivity
import com.thered.stocksignal.R


class AutoTradeFragment : Fragment() {

    private lateinit var conditionAdapter: ConditionAdapter
    private val conditionList = mutableListOf<Condition>() // 리스트에 임의 데이터 추가

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auto_trade, container, false) // fragment_auto_trade로 변경
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        val recyclerView: RecyclerView = view.findViewById(R.id.conditions_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        conditionAdapter = ConditionAdapter(conditionList)
        recyclerView.adapter = conditionAdapter

        // 임의 데이터 추가
        setupInitialConditions()

        // 버튼 클릭 리스너
        val addButton: ImageButton = view.findViewById(R.id.add_condition)
        val removeButton: ImageButton = view.findViewById(R.id.remove_condition)

        // 조건 추가 버튼 클릭 시
        addButton.setOnClickListener {
            val intent = Intent(requireContext(), NewScenarioConditionActivity::class.java)
            startActivity(intent)
            conditionList.add(Condition("새 조건", "+0.00%"))  // 새로운 조건 추가
            conditionAdapter.notifyItemInserted(conditionList.size - 1)
        }

        // 조건 제거 버튼 클릭 시
        removeButton.setOnClickListener {
            if (conditionList.isNotEmpty()) {
                conditionList.removeAt(conditionList.size - 1)  // 마지막 항목 제거
                conditionAdapter.notifyItemRemoved(conditionList.size)
            }
        }

        // 적용하기 버튼 클릭
        val applyButton: Button = view.findViewById(R.id.apply_button)
        applyButton.setOnClickListener {
            // 적용하기 로직 추가
        }
    }

    // 임의 데이터를 리스트에 추가하는 함수
    private fun setupInitialConditions() {
        conditionList.add(Condition("조건명", "수익률"))
        conditionList.add(Condition("조건명", "수익률"))
        conditionAdapter.notifyDataSetChanged()  // 데이터가 추가되었음을 어댑터에 알림
    }
}
