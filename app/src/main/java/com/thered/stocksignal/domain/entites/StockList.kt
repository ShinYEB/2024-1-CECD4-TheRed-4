package com.thered.stocksignal.domain.entites

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StockList(
    @SerializedName("items")
    var stocks: List<Stock>,
) : Serializable {
    constructor() : this(listOf())
}