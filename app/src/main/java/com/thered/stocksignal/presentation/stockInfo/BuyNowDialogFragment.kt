package com.thered.stocksignal.presentation.stockinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.thered.stocksignal.R

class BuyNowDialogFragment : DialogFragment() {
    private lateinit var buy_price_text: EditText
    private lateinit var buy_quantity_hint: EditText
    private var onConfirmClickListener: ((String, String) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 다이얼로그 너비를 화면의 80%로 설정
        dialog?.window?.setLayout((resources.displayMetrics.widthPixels * 1).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_buy_now, container, false)

        // EditText 초기화
        buy_price_text = view.findViewById(R.id.buy_price_text)
        buy_quantity_hint = view.findViewById(R.id.buy_quantity_hint)

        // 구매하기 버튼 클릭 시
        val confirmButton: Button? = view.findViewById(R.id.buy_button)
        confirmButton?.setOnClickListener {
            // 입력값이 유효한지 체크
            val price = buy_price_text.text.toString()
            val quantity = buy_quantity_hint.text.toString()

            if (price.isNotEmpty() && quantity.isNotEmpty()) {
                onConfirmClickListener?.invoke(price, quantity) // 가격과 수량을 전달
                dismiss()  // 다이얼로그 닫기
            } else {
                Toast.makeText(activity, "가격과 수량을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }
        // 닫기 버튼 클릭 시
        val cancelButton: Button = view.findViewById(R.id.close_button)
        cancelButton.setOnClickListener {
            dismiss()
        }

        // Sell 버튼 클릭 시 SellNowDialogFragment로 이동
        val sellButton: Button = view.findViewById(R.id.buy_sell_change_button)
        sellButton.setOnClickListener {
            // SellNowDialogFragment로 이동
            val sellDialog = SellNowDialogFragment()
            sellDialog.show(requireFragmentManager(), "SellNowDialog")
            dismiss()  // 현재 다이얼로그는 닫기
        }

        return view
    }
    fun setOnConfirmClickListener(listener: (String, String) -> Unit) {
        this.onConfirmClickListener = listener
    }
}
