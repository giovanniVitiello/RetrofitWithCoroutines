package com.example.retrofit_coroutines

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "35160723af91450bb52cb10b2a3c88ba"

class RetrofiteService {
    //the okhttp Interceptor, need to view in the logCat the response of the server
    //in json file

    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://openexchangerates.org/api/")
        .client(client)
        .build()

    private val service: RetrofitServiceInterface =
        retrofit.create(RetrofitServiceInterface::class.java)

    suspend fun moneyDetail(): Double? {
        val response = service.getEuroMoneyDetail(app_id = API_KEY, base = "USD")
        val success = response.body()

        if (success != null) {
            return success.toMoneyUtil()
        } else {
            throw Exception(response.errorBody().toString())
        }
    }
}


interface RetrofitServiceInterface {
    @GET("latest.json")
    fun getEuroMoney(@Query("app_id") app_id: String, @Query("base") base: String): Call<Money>

    @GET("latest.json")
    suspend fun getEuroMoneyDetail(
        @Query("app_id") app_id: String,
        @Query("base") base: String
    ): Response<Money>
}
