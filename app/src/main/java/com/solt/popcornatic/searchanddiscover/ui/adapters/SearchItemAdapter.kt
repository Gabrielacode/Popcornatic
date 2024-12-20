package com.solt.popcornatic.searchanddiscover.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.SearchItemLayoutBinding
import com.solt.popcornatic.movies.ui.fragments.MOVIE_ID
import com.solt.popcornatic.searchanddiscover.data.remote.models.SearchItem
import com.solt.popcornatic.searchanddiscover.data.remote.models.SearchItemType
import com.solt.popcornatic.tvshows.ui.fragments.TV_SHOWS_ID

val searchItemDiffUtil = object:DiffUtil.ItemCallback<SearchItem>(){
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return  oldItem == newItem
    }

}
class SearchItemAdapter():PagingDataAdapter<SearchItem,SearchItemAdapter.SearchItemViewHolder>(
    searchItemDiffUtil) {
    class SearchItemViewHolder(val binding:SearchItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item:SearchItem?){
          if(item!=null){
              binding.apply {
                  Glide.with(imageView).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${item.posterPath?:item.backdropPath}").into(imageView)
                  nameOfItem.text = item.title
                  searchType.text = item.searchItemType.title
                  binding.root.setOnClickListener {
                      when(item.searchItemType){
                          SearchItemType.MOVIE -> {
                              val bundle = bundleOf(MOVIE_ID to item.id)
                              binding.root.findNavController().navigate(R.id.action_searchAndDiscoverPage_to_movieDetailsPage,bundle,null)
                          }
                          SearchItemType.TVSHOW -> {
                              val bundle = bundleOf(TV_SHOWS_ID to item.id)
                              binding.root.findNavController().navigate(R.id.action_searchAndDiscoverPage_to_tvDetailsPage,bundle,null)
                          }
                      }
                  }
              }
          }
        }
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = SearchItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchItemViewHolder((binding))
    }
}