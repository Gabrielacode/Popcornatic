package com.solt.popcornatic.tvshows.ui.adapters

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
import com.solt.popcornatic.databinding.TrendingMovieItemBinding
import com.solt.popcornatic.movies.data.model.MovieResult
import com.solt.popcornatic.movies.ui.adapter.MovieItemActions
import com.solt.popcornatic.movies.ui.adapter.MovieViewHolder
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.TvShowResult

val tvShowsResultDiffUtil = object: DiffUtil.ItemCallback<TvShowResult>() {
    override fun areItemsTheSame(oldItem: TvShowResult, newItem: TvShowResult): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TvShowResult, newItem: TvShowResult): Boolean {
        return  oldItem == newItem
    }

}
class TvShowsListAdapter(val createTvShowViewHolder:( LayoutInflater,ViewGroup,TvShowItemActions)->TvShowViewHolder, val tvShowItemActions: TvShowItemActions):PagingDataAdapter<TvShowResult,TvShowViewHolder>(tvShowsResultDiffUtil) {
    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(this.getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return createTvShowViewHolder(LayoutInflater.from(parent.context),parent,tvShowItemActions)
    }
}
 abstract class TvShowViewHolder(val view:View):ViewHolder(view){
     abstract fun bind(result:TvShowResult?)
 }
interface TvShowItemActions{
    fun onClick(view:View,tvShowId: Int)
}
class AiringTodayTvShowViewHolder(val binding: TrendingMovieItemBinding, val tvShowItemActions: TvShowItemActions):
    TvShowViewHolder(binding.root) {
    companion object{
        fun create(layoutInflater: LayoutInflater, parent: ViewGroup, tvShowItemActions: TvShowItemActions):AiringTodayTvShowViewHolder{
            return AiringTodayTvShowViewHolder(TrendingMovieItemBinding.inflate(layoutInflater,parent,false) , tvShowItemActions)

        }
    }

    override fun bind(result: TvShowResult?) {
        if(result!=null){
            Glide.with(binding.movieposter).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${result.backdropPath?:result.posterPath}").into(binding.movieposter)
            binding.titleTv.text = result.name
            binding.descriptionTv.text = result.overview
            binding.root.setOnClickListener {
               tvShowItemActions.onClick(binding.movieposter,result.id)
            }
        }
    }
}

class BaseTvShowViewHolder(val binding: MainpageItemBinding, val tvShowItemActions: TvShowItemActions):TvShowViewHolder(binding.root) {
    companion object{
        fun create(layoutInflater: LayoutInflater,parent: ViewGroup,tvShowItemActions: TvShowItemActions): BaseTvShowViewHolder {
            return BaseTvShowViewHolder(MainpageItemBinding.inflate(layoutInflater,parent,false),tvShowItemActions)

        }
    }

    override fun bind(result: TvShowResult?) {
        if(result!=null){
            Glide.with(binding.posterImage).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${result.posterPath ?: result.backdropPath}").into(binding.posterImage)
            binding.root.setOnClickListener {
                tvShowItemActions.onClick(binding.posterImage,result.id)
            }
        }
    }
}