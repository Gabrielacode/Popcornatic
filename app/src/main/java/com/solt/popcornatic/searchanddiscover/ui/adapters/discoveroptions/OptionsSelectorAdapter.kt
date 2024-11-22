package com.solt.popcornatic.searchanddiscover.ui.adapters.discoveroptions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.ItemLayoutBinding
import com.solt.popcornatic.searchanddiscover.ui.viewmodels.DiscoverOptions
val optionsDiffUtil = object:DiffUtil.ItemCallback<DiscoverOptions.Options.MovieOptions>(){
    override fun areItemsTheSame(
        oldItem: DiscoverOptions.Options.MovieOptions,
        newItem: DiscoverOptions.Options.MovieOptions
    ): Boolean {
       return oldItem.ordinal  == newItem.ordinal
    }

    override fun areContentsTheSame(
        oldItem: DiscoverOptions.Options.MovieOptions,
        newItem: DiscoverOptions.Options.MovieOptions
    ): Boolean {
        return  oldItem == newItem
    }


}
class MovieOptionsSelectorAdapter(val onClick:(DiscoverOptions.Options.MovieOptions ,View)->Unit):ListAdapter<DiscoverOptions.Options.MovieOptions,MovieOptionsSelectorAdapter.ItemViewHolder>(
    optionsDiffUtil) {
     inner class ItemViewHolder(val binding:ItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
     fun bind(option:DiscoverOptions.Options.MovieOptions){
         binding.root.apply {
             text = option.title
             setOnClickListener {
                 onClick(option,it)
             }
         }
     }
    }
    init {
        submitList(DiscoverOptions.Options.MovieOptions.entries.toList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}