package com.thered.stocksignal.presentation.mystock

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thered.stocksignal.R
import com.thered.stocksignal.presentation.StockInfoActivity
import com.thered.stocksignal.presentation.mystock.placeholder.MyStockPlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class MystockCoverFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stock_cover_list, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.list)

        // Set the adapter
        if (recyclerView is RecyclerView) {
            with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                // 어댑터 설정 시 클릭 리스너 추가
                adapter = MystockCoverRecyclerViewAdapter(MyStockPlaceholderContent.ITEMS) { clickedItem ->
                    // 클릭된 항목에 대해 처리
                    val intent = Intent(requireContext(), StockInfoActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MystockCoverFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}