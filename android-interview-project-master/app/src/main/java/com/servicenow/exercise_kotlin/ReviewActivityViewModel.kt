package com.servicenow.exercise_kotlin

import android.app.Application
import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

class ReviewActivityViewModel(application: Application) : AndroidViewModel(application) {

    //  local variable to hold newly created latlng values from Address by Geo-coding
    private lateinit var latlng: LatLng

    /**
     * function for geo-coding the address into LatLng values
     */
    fun getLocationFromAddress(context: Context, strAddress: String): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var latLng: LatLng? = null
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }

            val location = address[0]
            latLng = LatLng(location.getLatitude(), location.getLongitude())
            latlng = latLng
        } catch (ex: IOException) {

            ex.printStackTrace()
        }
        return latLng
    }
}