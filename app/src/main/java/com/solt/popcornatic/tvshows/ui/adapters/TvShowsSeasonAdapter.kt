package com.solt.popcornatic.tvshows.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solt.popcornatic.databinding.TvshowSeasonItemBinding
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Season
val seasonDiffUtil = object: DiffUtil.ItemCallback<Season>(){
    override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
       return oldItem.seasonNumber == newItem.seasonNumber
    }

    override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
       return oldItem == newItem
    }

}
class TvShowsSeasonAdapter():ListAdapter<Season,TvShowsSeasonAdapter.SeasonViewHolder>(seasonDiffUtil) {
    inner class SeasonViewHolder(val binding:TvshowSeasonItemBinding):ViewHolder(binding.root){
      fun bind(season:Season){
          binding.apply {
              title.text = "Season ${season.seasonNumber}"
              listOfEpisodes.visibility = View.GONE
              root.setOnClickListener {
                  when(listOfEpisodes.visibility){
                      View.GONE,View.INVISIBLE -> {
                          listOfEpisodes.visibility = View.VISIBLE
                      }

                      View.VISIBLE -> {
                          listOfEpisodes.visibility = View.GONE
                      }
                  }
              }
          }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val binding = TvshowSeasonItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  SeasonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
       holder.bind(getItem(position))
    }
}