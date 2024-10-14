package com.thered.stocksignal.data.network

import com.thered.stocksignal.domain.entites.StockBalance
import com.thered.stocksignal.domain.entites.StockList
import retrofit2.Call
import retrofit2.http.GET

interface StockService {
    @GET("testapp/api/main/origin")
    fun getStockList(): Call<StockList>

    @GET("testapp/api/mybalance")
    fun getMyBalance(): Call<StockBalance>
}