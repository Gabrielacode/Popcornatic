package com.solt.popcornatic.tvshows.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.MovieItemBinding
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.JointClasses.RecommendedMovies
import com.solt.popcornatic.movies.ui.adapter.MovieDetailRecommendationsAdapter
import com.solt.popcornatic.movies.ui.adapter.MovieItemActions
import com.solt.popcornatic.movies.ui.adapter.recommendationsDiffUtil
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.JointClasses.RecommendedTvShows
import com.solt.popcornatic.tvshows.ui.fragments.TV_SHOWS_ID

val recommendationsTvShowsDiffUtil = object : DiffUtil.ItemCallback<RecommendedTvShows> () {




    override fun areItemsTheSame(
        oldItem: RecommendedTvShows,
        newItem: RecommendedTvShows
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: RecommendedTvShows,
        newItem: RecommendedTvShows
    ): Boolean {
        return oldItem == newItem
    }
}
class TvShowRecommendationsAdapter:
    PagingDataAdapter<RecommendedTvShows, TvShowRecommendationsAdapter.TvShowsRecommendationsViewHolder>(
    recommendationsTvShowsDiffUtil
    ) {
    inner  class TvShowsRecommendationsViewHolder(val binding: MovieItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShows: RecommendedTvShows?) {
            if (tvShows!=null) {
                Glide.with(binding.posterImage).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${tvShows.posterPath?:tvShows.backdropPath}").into(binding.posterImage)
                binding.root.setOnClickListener {
                    val bundle = bundleOf(TV_SHOWS_ID to tvShows.id)
                    it.findNavController().navigate(R.id.action_tvDetailsPage_self,bundle)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: TvShowsRecommendationsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsRecommendationsViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TvShowsRecommendationsViewHolder(binding)
    }
}