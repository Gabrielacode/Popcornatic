package com.solt.popcornatic.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.databinding.MovieItemBinding
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.JointClasses.RecommendedMovies
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Recommendations.MovieRecommendationsResult

val recommendationsDiffUtil = object : DiffUtil.ItemCallback<RecommendedMovies> (){

    override fun areItemsTheSame(oldItem: RecommendedMovies, newItem: RecommendedMovies): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: RecommendedMovies,
        newItem: RecommendedMovies
    ): Boolean {
        return  oldItem == newItem
    }

}
class MovieDetailRecommendationsAdapter(val movieItemActions: MovieItemActions):PagingDataAdapter<RecommendedMovies,MovieDetailRecommendationsAdapter.MovieRecommendationsViewHolder>(
    recommendationsDiffUtil) {
    inner  class MovieRecommendationsViewHolder(val binding: MovieItemBinding, val movieItemActions: MovieItemActions):ViewHolder(binding.root) {
      fun bind(movieRecommendations: RecommendedMovies?) {
            if (movieRecommendations!=null) {
                Glide.with(binding.posterImage).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${movieRecommendations.posterPath?:movieRecommendations.backdropPath}").into(binding.posterImage)
                binding.root.setOnClickListener {
                    movieItemActions.onClick(binding.posterImage,movieRecommendations.id)
                }
            }
        }
}

    override fun onBindViewHolder(holder: MovieRecommendationsViewHolder, position: Int) {
         holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieRecommendationsViewHolder {
       val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieRecommendationsViewHolder(binding,movieItemActions)
    }
}