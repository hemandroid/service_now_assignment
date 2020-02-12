package com.servicenow.exercise_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.servicenow.exercise.R
import com.servicenow.exercise.databinding.ActivityReviewBinding
import com.servicenow.exercise_kotlin.utils.Utils


class ReviewActivity : AppCompatActivity(), OnMapReadyCallback {

    //  Google map variable
    private lateinit var map: GoogleMap

    //  ViewModel variable initialization
    private lateinit var reviewActivityViewModel: ReviewActivityViewModel

    //  LatLng values to get extract from the viewmodel function
    private lateinit var latlng: LatLng

    //  Variable to define the mapView from XML
    private lateinit var mapView: MapView

    //  Empty variable to catch title coming from ListActivity by Intent
    private var storeName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * ViewModel declaration
         */
        reviewActivityViewModel = ViewModelProvider(this).get(ReviewActivityViewModel::class.java)

        /**
         * Adding databinding to local variable to be used within onCreate method
         */
        val activityReviewBinding =
            DataBindingUtil.setContentView<ActivityReviewBinding>(this, R.layout.activity_review)

        /**
         * Receiving the incoming data with the help of intent
         * Storing into local final @param val variables
         */

        intent?.apply {
            val coffeeReview = getStringExtra("review")
            val coffeeRating = getIntExtra("rating", 0)
            val storeLocation = getStringExtra("location")
            storeName = getStringExtra("name")

            /**
             * Appending data to view items
             */
            activityReviewBinding.coffeeReview.text = coffeeReview
            activityReviewBinding.coffeeRating.rating = coffeeRating.toFloat()
            activityReviewBinding.location.text = storeLocation
            activityReviewBinding.coffeeStoreImage.setImageResource(
                Utils.getIconResourceFromName(
                    storeName.toString()
                )
            )
            /**
             * Fetching latlng values by Geo-coding the address from ViewModel method
             */
            latlng =
                reviewActivityViewModel.getLocationFromAddress(this@ReviewActivity, storeLocation)!!
        }
        /**
         * Loading the mapView
         */
        loadMapView(activityReviewBinding, savedInstanceState)

        /**
         * Adding custom title and back button to AppBar
         */
        supportActionBar?.also {
            it.title = storeName
            it.setDisplayHomeAsUpEnabled(true)
        }
    }

    /**
     * function to create mapView for Google Maps
     */
    private fun loadMapView(
        activityReviewBinding: ActivityReviewBinding,
        savedInstanceState: Bundle?
    ) {
        activityReviewBinding.storeMapView.onCreate(savedInstanceState)
        activityReviewBinding.storeMapView.getMapAsync(this@ReviewActivity)
        mapView = activityReviewBinding.storeMapView
    }

    /**
     * function for back button pressed
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * OnMapReady callback function for Google Maps
     */
    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(this)
        map = googleMap
        if (::map.isInitialized.not()) return
        with(map) {
            moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 13f))
            addMarker(MarkerOptions().position(latlng).title(storeName))
            mapType = GoogleMap.MAP_TYPE_NORMAL
            map.setOnMarkerClickListener { marker ->
                if (marker.isInfoWindowShown) {
                    marker.hideInfoWindow()
                } else {
                    marker.showInfoWindow()
                }
                true
            }
        }
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mapView.onLowMemory()
        super.onLowMemory()
    }
}
