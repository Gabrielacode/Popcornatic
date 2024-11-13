package com.solt.popcornatic.movies.data.model.MovieDetailPackage.Videos


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailVideos(
    @SerialName("id")
    val id: Int?,
    @SerialName("results")
    val videoResults: List<VideoResult?>?
)