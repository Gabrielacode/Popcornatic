package com.solt.popcornatic.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solt.popcornatic.databinding.GenreListItemBinding
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Genre

val genreDiffUtil = object: DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return  oldItem == newItem
    }

}
class MovieDetailGenreListAdapter:ListAdapter<Genre,MovieDetailGenreListAdapter.GenreViewHolder>(
    genreDiffUtil) {
    inner class GenreViewHolder(val binding:GenreListItemBinding):ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = GenreListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
          holder.binding.apply {
              val genre = getItem(position)
              genreBtn.text = "#${genre.name}"
          }
    }
}