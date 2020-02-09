package com.servicenow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.servicenow.coffee.model.CoffeeShopDetailResponse
import com.servicenow.exercise.R
import com.servicenow.exercise.databinding.CoffeeShopItemBinding
import com.servicenow.exercise_kotlin.OnItemClickListener
import kotlinx.android.synthetic.main.coffee_shop_item.view.*

/**
 * Adapter class is holding @param onItemClickListener for the recyclerview items as a primary constructor object
 */
class CoffeeShopListRecyclerAdapter(private var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CoffeeShopListRecyclerAdapter.CoffeeShopViewHolder>() {
    /**
     * Local Arraylist to load the data into the model object, by receiving from Activity
     */
    var coffeeShopList = ArrayList<CoffeeShopDetailResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeShopViewHolder {
        val coffeeShopItemBinding: CoffeeShopItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.coffee_shop_item, parent, false
        )
        return CoffeeShopViewHolder(coffeeShopItemBinding)
    }

    /**
     * Counting the number of items from an ArrayList
     */
    override fun getItemCount(): Int {
        return coffeeShopList.size
    }

    override fun onBindViewHolder(holder: CoffeeShopViewHolder, position: Int) {
        val item = coffeeShopList.get(position)
        holder.onBind(item, onItemClickListener)
    }

    /**
     * Fetching the List of JSON data from Activity to adapter class
     */
    fun setDataList(list: ArrayList<CoffeeShopDetailResponse>) {
        coffeeShopList = list
        notifyDataSetChanged()
    }

    /**
     * ViewHolder class to fetch the list item binding from adapter class
     */
    class CoffeeShopViewHolder(itemView: CoffeeShopItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        fun onBind(get: CoffeeShopDetailResponse, callBack: OnItemClickListener) {
            with(itemView) {
                tv_coffee_shop_name.text = get.name
                tv_coffee_shop_address.text = get.location
                tv_store_rating.rating = get.rating!!.toFloat()
                coffee_shop_image.setImageResource(getIconResourceFromName(get.name.toString()))
                listItem.setOnClickListener {
                    callBack.onItemClick(adapterPosition)
                }
            }
        }
    }

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