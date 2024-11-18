package com.solt.popcornatic.tvshows.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.TvShowEpisodeItemBinding
import com.solt.popcornatic.tvshows.data.remote.model.seasonsandepisodes.Season.Episode
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Season
import com.solt.popcornatic.tvshows.ui.fragments.EPISODE_NUMBER
import com.solt.popcornatic.tvshows.ui.fragments.SEASON_NUMBER
import com.solt.popcornatic.tvshows.ui.fragments.TVSERIES_ID

class TvShowsEpisodeAdapter(val tvSeriesId:Int, val season: Season):
    RecyclerView.Adapter<TvShowsEpisodeAdapter.EpisodeViewHolder>() {
    inner class EpisodeViewHolder(val binding: TvShowEpisodeItemBinding):ViewHolder(binding.root){
        fun bind( episodeNumber:Int){
            binding.button.text = episodeNumber.toString()
            binding.button.setOnClickListener {
                val bundle = bundleOf(
                    TVSERIES_ID to tvSeriesId,
                    SEASON_NUMBER to season.seasonNumber,
                    EPISODE_NUMBER to episodeNumber
                )
                it.findNavController().navigate(R.id.action_tvShowSeasonandEpisodesPage_to_tvShowEpisodeDetailPage,bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = TvShowEpisodeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  EpisodeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return season.episodeCount?:0
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(position)
    }
}