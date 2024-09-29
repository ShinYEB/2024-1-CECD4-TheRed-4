package com.thered.stocksignal.Presentation.Home

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.replace
import com.thered.stocksignal.Presentation.MyStock.MyStockActivity
import com.thered.stocksignal.R

class HomeFragment : Fragment() {


    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val button = view.findViewById<Button>(R.id.mystock_button)

        button.setOnClickListener {
            val intent = Intent(requireContext(), MyStockActivity::class.java)
            startActivity(intent)
        }

        childFragmentManager.beginTransaction()
            .replace(R.id.stock_list, StockCoverFragment())
            .commit()

        return view
    }
}