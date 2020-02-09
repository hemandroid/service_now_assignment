package com.servicenow.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBaseFactory {
    private const val hostURL = "https://jsonblob.com/api/jsonBlob/"

    fun makeRetrofitServiceCall(): RetrofitBaseService {
        return Retrofit.Builder()
            .baseUrl(hostURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitBaseService::class.java)
    }
}