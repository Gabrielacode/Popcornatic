package com.solt.popcornatic.movies.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.databinding.MovieImageListItemBinding
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Images.Image
val imageDiffUtil = object:DiffUtil.ItemCallback<Image>(){
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
       return oldItem.filePath == newItem.filePath
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

}
class MovieImageListAdapter():ListAdapter<Image,MovieImageListAdapter.ImageViewHolder>(imageDiffUtil) {
    inner class ImageViewHolder(val binding:MovieImageListItemBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = MovieImageListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        holder.binding.apply {
            Glide.with(this.image).load("${BASE_IMAGE_URL}${POSTER_IMAGE_SIZE}${image.filePath}").into(this.image)
            imageVoteCount.text = image.voteCount.toString()
            imageVoteAvg.text = image.voteAvg.toString()
        }
    }
}