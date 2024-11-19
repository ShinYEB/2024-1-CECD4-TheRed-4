package com.thered.stocksignal.presentation.stockinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thered.stocksignal.R
import com.thered.stocksignal.Data.model.Condition

class ConditionAdapter(private val conditionList: List<com.thered.stocksignal.Data.model.Condition>) :
    RecyclerView.Adapter<ConditionAdapter.ConditionViewHolder>() {

    class ConditionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val conditionName1: TextView = view.findViewById(R.id.condition_name_1)
        val profitRate1: TextView = view.findViewById(R.id.profit_rate_1)
        val conditionName2: TextView = view.findViewById(R.id.condition_name_2)
        val profitRate2: TextView = view.findViewById(R.id.profit_rate_2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConditionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_condition, parent, false)
        return ConditionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConditionViewHolder, position: Int) {
        val condition = conditionList[position]

        // 첫 번째 조건 설정
        holder.conditionName1.text = condition.name
        holder.profitRate1.text = condition.profitRate

        // 두 번째 조건이 있을 경우만 설정
        if (condition.name2 != null && condition.profitRate2 != null) {
            holder.conditionName2.text = condition.name2
            holder.profitRate2.text = condition.profitRate2
            holder.conditionName2.visibility = View.VISIBLE
            holder.profitRate2.visibility = View.VISIBLE
        } else {
            holder.conditionName2.visibility = View.GONE
            holder.profitRate2.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return conditionList.size
    }
}
