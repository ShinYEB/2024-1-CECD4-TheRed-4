//package com.thered.stocksignal.Presentation.StockInfo
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.Toast
//import androidx.fragment.app.FragmentTransaction
//import com.thered.stocksignal.R
//
//class SellNowDialogFragment : BasePriceDialogFragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = super.onCreateView(inflater, container, savedInstanceState)
//
//        // BUY 버튼 클릭 시 BuyNowDialogFragment로 이동
//        val buyChangeButton: Button = view?.findViewById(R.id.sell_buy_change_button) ?: return view
//        buyChangeButton.setOnClickListener {
//            val fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.fragment_container, BuyNowDialogFragment()) // fragment_container는 프래그먼트가 배치될 컨테이너 ID
//            fragmentTransaction.addToBackStack(null) // 뒤로 가기 스택에 추가
//            fragmentTransaction.commit()
//        }
//
//        return view
//    }
//
//    override fun onConfirmButtonClick() {
//        val inputPrice = priceInput.text.toString()
//        if (inputPrice.isNotEmpty()) {
//            Toast.makeText(activity, "판매 가격: $inputPrice", Toast.LENGTH_SHORT).show()
//            dismiss() // 다이얼로그 닫기
//        } else {
//            Toast.makeText(activity, "판매 가격을 입력하세요.", Toast.LENGTH_SHORT).show()
//        }
//    }
//}
