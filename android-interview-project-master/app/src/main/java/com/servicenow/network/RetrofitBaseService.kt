package com.servicenow.network

import com.servicenow.coffee.model.CoffeeShopDetailResponse
import retrofit2.http.GET

interface RetrofitBaseService {

    @GET("c1a89a37-371e-11ea-a549-6f3544633231")
    suspend fun getListOfCoffeeShops(): List<CoffeeShopDetailResponse>
}
