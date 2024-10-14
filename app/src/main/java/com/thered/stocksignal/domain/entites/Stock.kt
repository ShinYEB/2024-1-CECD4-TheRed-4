package com.thered.stocksignal.domain.entites

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Stock(
    @SerializedName("code")
    var code: String,
    @SerializedName("stockName")
    var stockName: String,
    @SerializedName("quantity")
    var quantity: Int,
    @SerializedName("avgPrice")
    var avgPrice: Int,
    @SerializedName("currentPrice")
    var currentPrice: Int,
    @SerializedName("startPrice")
    var startPrice: Int,
    @SerializedName("imageUrl")
    var imageUrl: String,
): Serializable {
    constructor(): this("", "",0,0,0,0,"")
}
