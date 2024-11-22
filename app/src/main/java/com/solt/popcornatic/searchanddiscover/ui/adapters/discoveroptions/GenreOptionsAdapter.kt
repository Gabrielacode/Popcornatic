package com.solt.popcornatic.searchanddiscover.ui.adapters.discoveroptions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.checkbox.MaterialCheckBox
import com.solt.popcornatic.databinding.SelectionListItemBinding
import com.solt.popcornatic.searchanddiscover.data.remote.models.DiscoverOptions.General.Genre.Genre
val genreDiffUtil = object :DiffUtil.ItemCallback<GenreWrapper>(){
    override fun areItemsTheSame(oldItem: GenreWrapper, newItem: GenreWrapper): Boolean {
        return oldItem.genre == newItem.genre
    }

    override fun areContentsTheSame(oldItem: GenreWrapper, newItem: GenreWrapper): Boolean {
       return  oldItem == newItem
    }

}
class GenreOptionsAdapter():ListAdapter<GenreWrapper,GenreOptionsAdapter.GenreViewHolder>(genreDiffUtil) {
    inner class GenreViewHolder(val binding:SelectionListItemBinding):ViewHolder(binding.root)
    {
        fun bind(genre: GenreWrapper){
            binding.apply {
                text.text = genre.genre.name
                materialCheckBox.addOnCheckedStateChangedListener { materialCheckBox, i ->
                    when(i){
                        MaterialCheckBox.STATE_CHECKED->{genre.isChecked =true}
                        MaterialCheckBox.STATE_UNCHECKED->{genre.isChecked = false}
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = SelectionListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
       holder.bind(getItem(position))
    }
}
data class GenreWrapper(val genre: Genre, var isChecked:Boolean)