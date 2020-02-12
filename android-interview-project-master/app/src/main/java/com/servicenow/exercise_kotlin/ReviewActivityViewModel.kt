package com.servicenow.exercise_kotlin

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

class ReviewActivityViewModel: ViewModel(){

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
            address ?: return null

            val location = address[0]
            latLng = LatLng(location.latitude, location.longitude)
        } catch (ex: IOException) {

            ex.printStackTrace()
        }
        return latLng
    }
}