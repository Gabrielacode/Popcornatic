package com.solt.popcornatic.movies.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.databinding.MovieMainpageItemBinding
import com.solt.popcornatic.databinding.TrendingMovieItemBinding
import com.solt.popcornatic.movies.data.model.MovieResult

val movieResultDiffUtil = object: DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
      return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
        return oldItem == newItem
    }

}
class MovieListAdapter(val createMovieViewHolder:( LayoutInflater,ViewGroup)->MovieViewHolder): PagingDataAdapter<MovieResult, MovieViewHolder>(movieResultDiffUtil) {
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(this.getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return createMovieViewHolder(LayoutInflater.from(parent.context),parent)
    }

}
abstract class MovieViewHolder(val view: View):ViewHolder(view){
    abstract fun bind(movieResult: MovieResult?)
}
class TrendingMovieViewHolder(val binding: TrendingMovieItemBinding):MovieViewHolder(binding.root) {
    override fun bind(movieResult: MovieResult?) {
        if (movieResult!=null) {
            Glide.with(binding.movieposter).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${movieResult.backdrop_path?:movieResult.poster_path}").into(binding.movieposter)
            binding.titleTv.text = movieResult.title
            binding.descriptionTv.text = movieResult.overview
        }
    }
    companion object{
        fun create(layoutInflater: LayoutInflater,parent: ViewGroup):TrendingMovieViewHolder{
            return TrendingMovieViewHolder(TrendingMovieItemBinding.inflate(layoutInflater,parent,false))

        }
    }
}
//Later on they might be different for each
class BaseMovieViewHolder(val binding: MovieMainpageItemBinding):MovieViewHolder(binding.root) {
    override fun bind(movieResult: MovieResult?) {
        if (movieResult!=null) {
            Glide.with(binding.posterImage).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${movieResult.poster_path?:movieResult.backdrop_path}").into(binding.posterImage)

        }
    }
    companion object{
        fun create(layoutInflater: LayoutInflater,parent: ViewGroup):BaseMovieViewHolder{
            return BaseMovieViewHolder(MovieMainpageItemBinding.inflate(layoutInflater,parent,false))

        }
    }
}