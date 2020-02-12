package com.servicenow.exercise_kotlin.utils

import com.servicenow.exercise.R

object Utils {
    /**
     * static method getIconResourceFromName to load local image files with Json Data based on Title
     */

    fun getIconResourceFromName(name: String) = when (name) {
        "Lofty" -> R.drawable.bean_bag
        "Zumbar" -> R.drawable.coffee_cup
        "Blue Bottle" -> R.drawable.coffee_grinder
        "Bird Rock" -> R.drawable.coffee_maker
        "Better Buzz Coffee" -> R.drawable.coffee_shop
        else -> -1
    }
}