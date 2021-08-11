package com.lucas.medical_equip.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
//import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://60abe9ca5a4de40017ccb2bd.mockapi.io"

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(APIWorker.client)
        .build()

    val apiService: APIService = getRetrofit().create(APIService::class.java)
}
