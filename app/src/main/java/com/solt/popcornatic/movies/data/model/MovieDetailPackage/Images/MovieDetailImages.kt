package com.solt.popcornatic.movies.data.model.MovieDetailPackage.Images


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailImages(
    @SerialName("backdrops")
    val backdrops: List<Backdrop?>?,
    @SerialName("id")
    val id: Int?,
    @SerialName("logos")
    val logos: List<Logo?>?,
    @SerialName("posters")
    val posters: List<Poster?>?
)