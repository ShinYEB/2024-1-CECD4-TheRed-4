package com.thered.stocksignal.presentation.stockinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thered.stocksignal.R

class ConditionAdapter(private val conditionList: List<Condition>) :
    RecyclerView.Adapter<ConditionAdapter.ConditionViewHolder>() {

    // ViewHolder 클래스에서 XML의 뷰 요소들을 초기화
    class ConditionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val conditionName1: TextView = view.findViewById(R.id.condition_name_1)
        val profitRate1: TextView = view.findViewById(R.id.profit_rate_1)
        val conditionName2: TextView = view.findViewById(R.id.condition_name_2)
        val profitRate2: TextView = view.findViewById(R.id.profit_rate_2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionViewHolder {
        // item_condition 레이아웃을 사용하여 뷰 생성
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_condition, parent, false)
        return ConditionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConditionViewHolder, position: Int) {
        // 현재 position의 데이터를 가져옴
        val condition = conditionList[position]

        // 첫 번째 조건명과 수익률 설정
        holder.conditionName1.text = condition.name
        holder.profitRate1.text = condition.profitRate

        // 두 번째 조건명과 수익률을 임의로 설정하거나 추가 데이터에 맞춰 설정 가능
        holder.conditionName2.text = "삼성전자"  // 임의의 조건명
        holder.profitRate2.text = "+15%"  // 임의의 수익률
    }

    override fun getItemCount(): Int {
        return conditionList.size
    }
}
