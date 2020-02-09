package com.servicenow.coffee.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Data class referencing to the JSON response object fields
 */
data class CoffeeShopDetailResponse(
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("review")
    @Expose
    var review: String? = null,

    @SerializedName("rating")
    @Expose
    var rating: Int? = null,

    @SerializedName("location")
    @Expose
    var location: String? = null
)