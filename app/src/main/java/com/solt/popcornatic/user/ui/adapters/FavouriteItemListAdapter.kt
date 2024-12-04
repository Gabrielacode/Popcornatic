package com.solt.popcornatic.user.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.databinding.FavouriteItemLayoutBinding
import com.solt.popcornatic.databinding.FavouriteListItemLayoutBinding

import com.solt.popcornatic.user.data.local.database.model.Item
  val favouriteItemDiffUtil = object:DiffUtil.ItemCallback<Item>(){
      override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
          return oldItem.itemId == newItem.itemId
      }

      override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
          return oldItem == newItem
      }

  }
class FavouriteItemListAdapter() :ListAdapter<Item,FavouriteItemListAdapter.FavouriteItemViewHolder>(
    favouriteItemDiffUtil) {
    inner class FavouriteItemViewHolder(val binding: FavouriteItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun  bind(item: Item){
            binding.apply {
                nameOfItem.text = item.name
                Glide.with(imageView).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${item.posterPath}").into(imageView)

            }
        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteItemViewHolder {
        val binding =
            FavouriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavouriteItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteItemViewHolder, position: Int) {
       holder.bind(getItem(position))
    }
}