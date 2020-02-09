package com.servicenow.exercise_kotlin.utils

import com.servicenow.exercise.R

class Utils {
    /**
     * static method getIconResourceFromName to load local image files with Json Data based on Title
     */
    companion object {
        @JvmStatic
        fun getIconResourceFromName(name: String): Int {
            when (name) {
                "Lofty" -> return R.drawable.bean_bag
                "Zumbar" -> return R.drawable.coffee_cup
                "Blue Bottle" -> return R.drawable.coffee_grinder
                "Bird Rock" -> return R.drawable.coffee_maker
                "Better Buzz Coffee" -> return R.drawable.coffee_shop
            }
            return -1
        }
    }
}