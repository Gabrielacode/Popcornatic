package com.solt.popcornatic.movies.data.model.MovieDetailPackage


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Credits(
    @SerialName("cast")
    val cast: List<Cast?>?,
    @SerialName("crew")
    val crew: List<Crew?>?
)