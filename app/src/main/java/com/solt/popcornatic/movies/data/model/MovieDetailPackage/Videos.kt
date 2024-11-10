package com.solt.popcornatic.movies.data.model.MovieDetailPackage


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Videos(
    @SerialName("results")
    val results: List<Result?>?
)