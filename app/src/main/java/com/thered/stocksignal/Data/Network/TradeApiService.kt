package com.thered.stocksignal.Data.Network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class BuyRequest(
    val scode: String,
    val orderType: String = "JIJUNG",  // 주문 유형을 기본값으로 설정
    val price: Int,
    val week: Int
)

data class SellRequest(
    val scode: String,
    val orderType: String = "JIJUNG",  // 주문 유형을 기본값으로 설정
    val price: Int,
    val week: Int
)

data class BuyResponse(
    val code: String,
    val result: String,
    val message: String
)

data class SellResponse(
    val code: String,
    val result: String,
    val message: String
)

interface BuyTradeApiService {
    @POST("/api/trade/buy")
    fun buyStock(
        @Header("Authorization") token: String,
        @Body request: BuyRequest
    ): Call<BuyResponse>
}

interface SellTradeApiService {
    @POST("/api/trade/sell")
    fun sellStock(
        @Header("Authorization") token: String,
        @Body request: SellRequest
    ): Call<SellResponse>
}