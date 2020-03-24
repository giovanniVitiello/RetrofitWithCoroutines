package com.example.retrofit_coroutines

import com.google.gson.annotations.SerializedName

data class Money(
    @SerializedName("base")
    val base: String,
    @SerializedName("disclaimer")
    val disclaimer: String?,
    @SerializedName("license")
    val license: String,
    @SerializedName("rates")
    val rates: Rates?,
    @SerializedName("timestamp")
    val timestamp: Long
) {
    data class Rates(
        @SerializedName("EUR")
        val eUR: Double?
    )

    /*function that convert Money data in MoneyUtil data, MoneyUtil is a class that take only necessary paramether*/
    fun toMoneyUtil(): Double? {
        return rates?.eUR
    }
}
