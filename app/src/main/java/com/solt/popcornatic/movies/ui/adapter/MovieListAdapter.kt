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
import com.solt.popcornatic.databinding.MainpageItemBinding
import com.solt.popcornatic.databinding.MovieItemBinding

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
class MovieListAdapter(val createMovieViewHolder:( LayoutInflater,ViewGroup,MovieItemActions)->MovieViewHolder, val movieItemActions: MovieItemActions): PagingDataAdapter<MovieResult, MovieViewHolder>(movieResultDiffUtil) {
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(this.getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return createMovieViewHolder(LayoutInflater.from(parent.context),parent,movieItemActions)
    }

}
abstract class MovieViewHolder(val view: View):ViewHolder(view){
    abstract fun bind(movieResult: MovieResult?)
}
interface MovieItemActions{

    fun onClick(view:View,movieId: Int)
}
class TrendingMovieViewHolder(val binding: TrendingMovieItemBinding, val movieItemActions: MovieItemActions):MovieViewHolder(binding.root) {
    override fun bind(movieResult: MovieResult?) {
        if (movieResult!=null) {
            Glide.with(binding.movieposter).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${movieResult.backdrop_path?:movieResult.poster_path}").into(binding.movieposter)
            binding.titleTv.text = movieResult.title
            binding.descriptionTv.text = movieResult.overview
            binding.root.setOnClickListener {
                movieItemActions.onClick(binding.movieposter,movieResult.id)
            }
        }
    }


    companion object{
        fun create(layoutInflater: LayoutInflater,parent: ViewGroup , movieItemActions: MovieItemActions):TrendingMovieViewHolder{
            return TrendingMovieViewHolder(TrendingMovieItemBinding.inflate(layoutInflater,parent,false) ,movieItemActions)

        }
    }
}
//Later on they might be different for each
class BaseMovieViewHolder(val binding: MainpageItemBinding,val movieItemActions: MovieItemActions):MovieViewHolder(binding.root) {
    override fun bind(movieResult: MovieResult?) {
        if (movieResult!=null) {
            Glide.with(binding.posterImage).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${movieResult.poster_path?:movieResult.backdrop_path}").into(binding.posterImage)
            binding.root.setOnClickListener {
                movieItemActions.onClick(binding.posterImage,movieResult.id)
            }
        }
    }
    companion object{
        fun create(layoutInflater: LayoutInflater,parent: ViewGroup,movieItemActions: MovieItemActions):BaseMovieViewHolder{
            return BaseMovieViewHolder(MainpageItemBinding.inflate(layoutInflater,parent,false),movieItemActions)

        }
    }
}
class PlainMovieViewHolder(val binding: MovieItemBinding,val movieItemActions: MovieItemActions):MovieViewHolder(binding.root) {
    override fun bind(movieResult: MovieResult?) {
        if (movieResult!=null) {
            Glide.with(binding.posterImage).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${movieResult.poster_path?:movieResult.backdrop_path}").into(binding.posterImage)
            binding.root.setOnClickListener {
                movieItemActions.onClick(binding.posterImage,movieResult.id)
            }
        }
    }
    companion object{
        fun create(layoutInflater: LayoutInflater,parent: ViewGroup,movieItemActions: MovieItemActions):PlainMovieViewHolder{
            return PlainMovieViewHolder(MovieItemBinding.inflate(layoutInflater,parent,false),movieItemActions)

        }
    }
}