package com.solt.popcornatic.movies.data.model.MovieDetailPackage.Credits


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailCredits(
    @SerialName("cast")
    val cast: List<Cast>?,
    @SerialName("crew")
    val crew: List<Crew>?,
    @SerialName("id")
    val id: Int?
)