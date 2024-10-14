package com.thered.stocksignal.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class Network {

    val serverUrl: String = "http://10.0.2.2:8000"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(serverUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}