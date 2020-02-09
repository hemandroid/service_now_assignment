package com.servicenow.repository

import com.servicenow.coffee.model.CoffeeShopDetailResponse
import com.servicenow.network.RetrofitBaseFactory

class CoffeeShopRepository {
    var retrofit = RetrofitBaseFactory.makeRetrofitServiceCall()

    suspend fun getCoffeeShopList(): List<CoffeeShopDetailResponse> = retrofit.getListOfCoffeeShops()
}