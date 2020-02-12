package com.servicenow.exercise_kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.servicenow.adapter.CoffeeShopListRecyclerAdapter
import com.servicenow.coffee.model.CoffeeShopDetailResponse
import com.servicenow.exercise.R
import com.servicenow.exercise.databinding.ActivityMainBinding
import com.servicenow.exercise_kotlin.model.ReviewListActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class ReviewListActivity : AppCompatActivity(), OnItemClickListener {
    /**
     * Initializing recyclerview adapter
     */
    private val coffeeShopListRecyclerAdapter by lazy { CoffeeShopListRecyclerAdapter(this) }

    /**
     * ViewModel variable initialization
     */
    private lateinit var coffeeShopViewModel: ReviewListActivityViewModel

    /**
     * local variable to hold the list of data from API response
     */
    private var ListOfCoffeeShop = ArrayList<CoffeeShopDetailResponse>()

    private lateinit var reviewListActivityBinding: ActivityMainBinding

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * ViewModel declaration
         */
        coffeeShopViewModel = ViewModelProvider(this).get(ReviewListActivityViewModel::class.java)

        /**
         * Adding databinding to local variable to be used within onCreate method
         */
        reviewListActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        reviewListActivityBinding.lifecycleOwner = this // This is only for two-way databinding

        // Progress bar indication while before loading the view
        reviewListActivityBinding.progressIndication.visibility = View.VISIBLE

        /**
         * Recycler-view layout manager and adapter initialization
         */
        setUpRecyclerView()

        /**
         * Checking Internet connectivity to load list of data
         */
        checkInternetConnectivity()
    }

    /**
     * @function to setup recyclerview
     */
    private fun setUpRecyclerView() {
        with(reviewListActivityBinding.coffeeShopList) {
            layoutManager = LinearLayoutManager(this@ReviewListActivity)
            reviewListActivityBinding.coffeeShopList.setHasFixedSize(true)
            reviewListActivityBinding.coffeeShopList.adapter = coffeeShopListRecyclerAdapter
        }
    }

    /**
     * @function to load the data by passing to adapter
     */
    private fun loadCoffeeShopsData() {
        coffeeShopViewModel.list.observe(this, Observer {
            // Hiding the progresss bar indication after successful completion of API call and
            // preparing the view to display
            reviewListActivityBinding.progressIndication.visibility = View.GONE
            ListOfCoffeeShop = it as ArrayList<CoffeeShopDetailResponse>

            // Passing data to recyclerview adapter for binding the views
            coffeeShopListRecyclerAdapter.setDataList(ListOfCoffeeShop)
        })
    }

    /**
     * Callback for itemClicklistener on Recyclerview
     * Intent operation from ListActivity to Review Activity
     */
    override fun onItemClick(position: Int) {
        val intent = Intent(this, ReviewActivity::class.java)
        intent.putExtra("name", ListOfCoffeeShop.get(position).name)
        intent.putExtra("review", ListOfCoffeeShop.get(position).review)
        intent.putExtra("rating", ListOfCoffeeShop.get(position).rating)
        intent.putExtra("location", ListOfCoffeeShop.get(position).location)
        startActivity(intent)
    }

    /**
     * @function to check Internet connectivity
     */
    private fun checkInternetConnectivity() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val isNetworkNotConnected =
                    intent!!.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
                if (isNetworkNotConnected) {
                    disconnected()
                } else {
                    connected()
                }
            }
        }
    }

    /**
     * @function for changing the view of disconnection with internet
     */
    private fun disconnected() {
        reviewListActivityBinding.coffeeShopList.visibility = View.GONE
        no_internet_connection_image.visibility = View.VISIBLE
    }

    /**
     * @function for changing the view of connection with internet and load the data
     */
    private fun connected() {
        reviewListActivityBinding.coffeeShopList.visibility = View.VISIBLE
        no_internet_connection_image.visibility = View.GONE
        loadCoffeeShopsData()
    }

    /**
     * @function to register the broadcastreceiver in OnStart
     */
    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    /**
     * @function to unregister the broadcastreceiver in OnStop
     */
    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}
