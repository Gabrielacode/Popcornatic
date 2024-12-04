package com.solt.popcornatic.user.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solt.popcornatic.databinding.FavouriteListItemLayoutBinding
import com.solt.popcornatic.user.data.local.database.model.Favourite
import com.solt.popcornatic.user.data.local.database.model.FavouriteAndItsItems

val favouriteDiffUtil = object:DiffUtil.ItemCallback<FavouriteAndItsItems>(){
    override fun areItemsTheSame(
        oldItem: FavouriteAndItsItems,
        newItem: FavouriteAndItsItems
    ): Boolean {
        return oldItem.favourite == newItem.favourite
    }

    override fun areContentsTheSame(
        oldItem: FavouriteAndItsItems,
        newItem: FavouriteAndItsItems
    ): Boolean {
        return oldItem == newItem

    }

}
class FavouriteListAdapter():PagingDataAdapter<FavouriteAndItsItems,FavouriteListAdapter.FavouriteViewHolder>(
    favouriteDiffUtil) {
    inner class FavouriteViewHolder(val binding:FavouriteListItemLayoutBinding):ViewHolder(binding.root)
    {
        fun bind(favourite: FavouriteAndItsItems?){
            binding.title.text = favourite?.favourite?.name?:"No Name"
            val favouriteAdapter = FavouriteItemListAdapter()
            binding.listOfItems.apply {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = favouriteAdapter
                favouriteAdapter.submitList(favourite?.listOfItems)
                Log.i("Hasd",favourite?.listOfItems.toString())
                visibility = GONE
            }
            binding.root.setOnClickListener {
                when(binding.listOfItems.visibility){
                    View.GONE,View.INVISIBLE -> binding.listOfItems.visibility = View.VISIBLE
                    View.VISIBLE -> binding.listOfItems.visibility = View.GONE
                }
            }

        }
    }
    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
      holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding =FavouriteListItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavouriteViewHolder(binding)
    }

}