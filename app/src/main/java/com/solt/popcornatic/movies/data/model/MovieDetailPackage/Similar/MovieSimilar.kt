package com.solt.popcornatic.movies.data.model.MovieDetailPackage.Similar


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieSimilar(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val movieSimilarResults: List<MovieSimilarResult?>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)