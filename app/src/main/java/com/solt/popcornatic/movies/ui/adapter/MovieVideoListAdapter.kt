package com.solt.popcornatic.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.solt.popcornatic.databinding.MovieVideoItemLayoutBinding
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Videos.VideoResult
val videoDiffUtil = object :DiffUtil.ItemCallback<VideoResult>(){
    override fun areItemsTheSame(oldItem: VideoResult, newItem: VideoResult): Boolean {
      return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: VideoResult, newItem: VideoResult): Boolean {
        return  oldItem == newItem
    }

}
class MovieVideoListAdapter( val lifecycle: Lifecycle):ListAdapter<VideoResult,MovieVideoListAdapter.MovieVideoHolder>(videoDiffUtil) {
    inner class MovieVideoHolder(val binding: MovieVideoItemLayoutBinding):ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVideoHolder {
        val binding = MovieVideoItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  MovieVideoHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieVideoHolder, position: Int) {
        val videoDetails = getItem(position)
        if (videoDetails.key != null){
            holder.binding.youtubePlayer.apply{
                lifecycle.addObserver(this)
                getYouTubePlayerWhenReady(object :YouTubePlayerCallback{
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                       youTubePlayer.cueVideo(videoDetails.key?:"",0.0f)
                    }

                })

            }


        }
    }
}