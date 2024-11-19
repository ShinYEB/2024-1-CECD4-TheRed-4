//package com.thered.stocksignal.Presentation.StockInfo
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.fragment.app.DialogFragment
//import com.thered.stocksignal.BuildConfig
//import com.thered.stocksignal.Data.Network.SellRequest
//import com.thered.stocksignal.Data.Network.SellResponse
//import com.thered.stocksignal.Data.Network.TradeApiService
//import com.thered.stocksignal.R
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class SellNowDialogFragment : DialogFragment() {
//
//    private lateinit var priceInput: EditText
//    private lateinit var weekInput: EditText
//    private val token = BuildConfig.API_TOKEN // BuildConfig에서 API_TOKEN 값을 가져옵니다
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.dialog_sell_now, container, false)
//        Log.d("SellNowDialogFragment", "Dialog layout inflated successfully") // 레이아웃 확인용 로그
//
//        // EditText 초기화
//        priceInput = view.findViewById(R.id.sell_price_text)
//        weekInput = view.findViewById(R.id.sell_quantity_hint)
//
//        // 구매하기 버튼 클릭 시
//        val confirmButton: Button? = view.findViewById(R.id.sell_button)
//        if (confirmButton == null) {
//            Log.d("SellNowDialogFragment", "Confirm button not found!")
//        } else {
//            Log.d("SellNowDialogFragment", "Confirm button found, setting click listener")
//            confirmButton.setOnClickListener {
//                // 버튼 클릭 시 로그 출력
//                Log.d("SellNowDialogFragment", "Confirm button clicked")  // 버튼 클릭 로그
//
//                // onConfirmButtonClick 호출
//                onConfirmButtonClick()
//            }
//        }
//
//        // 닫기 버튼 클릭 시
//        val cancelButton: Button = view.findViewById(R.id.close_button)
//        cancelButton.setOnClickListener {
//            dismiss()
//        }
//
//        return view
//    }
//
//
//    private fun onConfirmButtonClick() {
//        val scode = "005930"  // 실제 종목 코드를 설정
//        val price = priceInput.text.toString().toIntOrNull() ?: 0
//        val week = weekInput.text.toString().toIntOrNull() ?: 0
//
//        if (price > 0 && week > 0) {
//            val retrofit = Retrofit.Builder()
//                .baseUrl("http://pposiraun.com/") // 실제 API URL로 변경
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            val tradeApiService = retrofit.create(TradeApiService::class.java)
//            val sellRequest = sellRequest(scode = scode, price = price, week = week)
//
//            tradeApiService.sellStock("Bearer $token", sellRequest).enqueue(object : Callback<SellResponse> {
//                override fun onResponse(call: Call<SellResponse>, response: Response<SellResponse>) {
//                    if (response.isSuccessful) {
//                        val result = response.body()
//                        if (result != null && result.result == "SUCCESS") {
//                            Toast.makeText(activity, result.message, Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(
//                                activity,
//                                "구매 요청 실패: ${result?.message}",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    } else {
//
//                        Toast.makeText(activity, "구매 요청 실패: ${response.code()}", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                    dismiss()  // API 요청 결과에 따라 다이얼로그를 닫습니다.
//                }
//
//                override fun onFailure(call: Call<SellResponse>, t: Throwable) {
//                    Toast.makeText(activity, "네트워크 오류 발생: ${t.message}", Toast.LENGTH_SHORT).show()
//                    dismiss()  // 오류 발생 시에도 다이얼로그를 닫습니다.
//                }
//            })
//        } else {
//            Toast.makeText(activity, "가격과 주를 모두 입력하세요.", Toast.LENGTH_SHORT).show()
//        }
//    }
//}