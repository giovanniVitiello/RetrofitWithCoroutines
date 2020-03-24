package com.example.retrofit_coroutines

data class MoneyUtil(val rates: Rates?) {
    data class Rates(val eUR: Double?)
}

