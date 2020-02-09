package com.servicenow.exercise_kotlin.model

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.servicenow.coffee.model.CoffeeShopDetailResponse
import com.servicenow.exercise_kotlin.ReviewActivity
import com.servicenow.repository.CoffeeShopRepository

/**
 * ReviewListActivityViewModel is to represent the business logic and action's from HomeScreen
 * @param ReviewListActivityKt class*/
class ReviewListActivityViewModel(application: Application): AndroidViewModel(application) {
    private val coffeeShopRepository = CoffeeShopRepository()
    private var coffeeStoreList = ArrayList<CoffeeShopDetailResponse>()

    /** Calling LiveDataScope from CoroutineLiveDataKt to communicate with Repository,
     * to perform the API call and get the response*/
    var list = liveData{
        val coffeeShopList = coffeeShopRepository.getCoffeeShopList()
        emit(coffeeShopList)
        coffeeStoreList = coffeeShopList as ArrayList<CoffeeShopDetailResponse>
    }
    /**
     * @method to perform Intent operation from ListActivity to Review Activity
     */
    fun selectedCoffeeShop(position: Int, context: Context){
        val intent = Intent(context, ReviewActivity::class.java)
        intent.putExtra("name", coffeeStoreList.get(position).name)
        intent.putExtra("review", coffeeStoreList.get(position).review)
        intent.putExtra("rating", coffeeStoreList.get(position).rating)
        intent.putExtra("location", coffeeStoreList.get(position).location)
        context.startActivity(intent)
    }
}