package com.solt.popcornatic.tvshows.ui.adapters

import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.TvshowSeasonItemBinding
import com.solt.popcornatic.tvshows.data.remote.model.seasonsandepisodes.Season.Episode
import com.solt.popcornatic.tvshows.data.remote.model.seasonsandepisodes.Season.TvShowSeasonDetail
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Season
import com.solt.popcornatic.tvshows.ui.fragments.SEASON_NUMBER
import com.solt.popcornatic.tvshows.ui.fragments.TVSERIES_ID
import com.solt.popcornatic.tvshows.ui.viewmodels.TvShowsDetailPageViewModel
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction2

val seasonDiffUtil = object: DiffUtil.ItemCallback<Season>(){
    override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
       return oldItem.seasonNumber == newItem.seasonNumber
    }

    override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
       return oldItem == newItem
    }

}
class TvShowsSeasonAdapter( val tvSeriesId:Int
):ListAdapter<Season,TvShowsSeasonAdapter.SeasonViewHolder>(seasonDiffUtil) {
    inner class SeasonViewHolder(val binding:TvshowSeasonItemBinding):ViewHolder(binding.root){
      fun bind(season:Season){

          binding.apply {


              title.text = "Season ${season.seasonNumber}"
              listOfEpisodes.apply {
                  layoutManager = StaggeredGridLayoutManager( 6,StaggeredGridLayoutManager.VERTICAL)
                  visibility = View.GONE
                  adapter = TvShowsEpisodeAdapter(tvSeriesId,season)
              }

              root.setOnClickListener {
                  TransitionManager.beginDelayedTransition(it as ViewGroup)
                  when(listOfEpisodes.visibility){
                      View.GONE,View.INVISIBLE -> {
                          listOfEpisodes.visibility = View.VISIBLE
                      }

                      View.VISIBLE -> {

                          listOfEpisodes.visibility = View.GONE
                      }
                  }
              }
              root.setOnLongClickListener{
                  val bundle = bundleOf(
                      TVSERIES_ID to tvSeriesId,
                      SEASON_NUMBER to season.seasonNumber
                  )
                  it.findNavController().navigate(R.id.action_tvShowSeasonandEpisodesPage_to_tvShowSeasonDetailPage,bundle)
                  true
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