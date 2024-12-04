package com.solt.popcornatic.user.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.solt.popcornatic.databinding.SelectionListItemBinding
import com.solt.popcornatic.searchanddiscover.data.remote.models.DiscoverOptions.General.Genre.Genre
import com.solt.popcornatic.searchanddiscover.ui.adapters.discoveroptions.genreDiffUtil
import com.solt.popcornatic.user.data.local.database.model.Favourite
  val favouritesWrapperDiffUtil = object :DiffUtil.ItemCallback<FavouritesWrapper>(){
      override fun areItemsTheSame(
          oldItem: FavouritesWrapper,
          newItem: FavouritesWrapper
      ): Boolean {
          return  oldItem.favourite == newItem.favourite
      }

      override fun areContentsTheSame(
          oldItem: FavouritesWrapper,
          newItem: FavouritesWrapper
      ): Boolean {
          return  oldItem == newItem
      }
  }
class AddFavouritesAdapter():
       ListAdapter<FavouritesWrapper, AddFavouritesAdapter.FavouriteViewHolder>(
            favouritesWrapperDiffUtil) {
    inner class FavouriteViewHolder(val binding: SelectionListItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(favourite: FavouritesWrapper?) {
            if (favourite != null) {
                binding.apply {
                    text.text = favourite.favourite.name
                    materialCheckBox.addOnCheckedStateChangedListener { materialCheckBox, i ->
                        when (i) {
                            MaterialCheckBox.STATE_CHECKED -> {
                                favourite.isChecked = true
                            }

                            MaterialCheckBox.STATE_UNCHECKED -> {
                                favourite.isChecked = false
                            }
                        }
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):FavouriteViewHolder {
        val binding = SelectionListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
data class FavouritesWrapper(val favourite: Favourite, var isChecked:Boolean)