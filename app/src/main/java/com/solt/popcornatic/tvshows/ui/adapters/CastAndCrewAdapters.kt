package com.solt.popcornatic.tvshows.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.databinding.CastOrCrewLayoutItemBinding


import com.solt.popcornatic.movies.ui.adapter.castDiffUtil
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Credits.Cast
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Credits.Crew

val tvShowCastDiffUtil = object :DiffUtil.ItemCallback<Cast>(){
     override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
         return oldItem.id == newItem.id
     }

     override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
         return oldItem ==newItem
     }

 }
class CastListAdapter(): ListAdapter<Cast, CastListAdapter.CastViewHolder>(tvShowCastDiffUtil){
    inner class CastViewHolder(val binding: CastOrCrewLayoutItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = CastOrCrewLayoutItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = getItem(position)
        holder.binding.apply {
            Glide.with(profileImage).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${cast.profilePath}").into(profileImage)
            name.text = cast.originalName?:""
            role.text = cast.character?:""
        }
    }
}
val tvShowCrewDiffUtil = object :DiffUtil.ItemCallback<Crew>(){
    override fun areItemsTheSame(oldItem: Crew, newItem: Crew): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Crew, newItem: Crew): Boolean {
        return oldItem == newItem
    }

}
class CrewListAdapter():ListAdapter<Crew,CrewListAdapter.CrewViewHolder> (tvShowCrewDiffUtil){
    inner class CrewViewHolder(val binding: CastOrCrewLayoutItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewViewHolder {
        val binding = CastOrCrewLayoutItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CrewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
        val crew = getItem(position)
        holder.binding.apply {
            Glide.with(profileImage).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${crew.profilePath}").into(profileImage)
            name.text = crew.originalName?:""
            role.text = crew.job?:"None"
        }
    }
}