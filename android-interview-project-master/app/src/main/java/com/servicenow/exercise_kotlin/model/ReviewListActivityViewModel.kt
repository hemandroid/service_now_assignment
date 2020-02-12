package com.servicenow.exercise_kotlin.model

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.servicenow.coffee.model.CoffeeShopDetailResponse
import com.servicenow.exercise_kotlin.ReviewActivity
import com.servicenow.repository.CoffeeShopRepository

/**
 * ReviewListActivityViewModel is to represent the business logic and action's from HomeScreen
 * @param ReviewListActivityKt class*/
class ReviewListActivityViewModel: ViewModel() {
    private val coffeeShopRepository = CoffeeShopRepository()
    private var coffeeStoreList = ArrayList<CoffeeShopDetailResponse>()

    /** Calling LiveDataScope from CoroutineLiveDataKt to communicate with Repository,
     * to perform the API call and get the response*/
    var list = liveData{
        val coffeeShopList = coffeeShopRepository.getCoffeeShopList()
        emit(coffeeShopList)
        coffeeStoreList = coffeeShopList as ArrayList<CoffeeShopDetailResponse>
    }
}