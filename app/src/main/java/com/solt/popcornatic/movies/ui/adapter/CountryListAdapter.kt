package com.solt.popcornatic.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solt.popcornatic.databinding.CountryListItemBinding

 val stringDiffUtill = object:DiffUtil.ItemCallback<String>(){
     override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
          return oldItem == newItem
     }

     override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
         return areItemsTheSame(oldItem,newItem)
     }

 }
class CountryListAdapter():ListAdapter<String,CountryListAdapter.CountryViewHolder>(stringDiffUtill) {
    inner class CountryViewHolder(val binding:CountryListItemBinding):ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = CountryListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
          holder.binding.root.text = getItem(position)?:"None"
}}