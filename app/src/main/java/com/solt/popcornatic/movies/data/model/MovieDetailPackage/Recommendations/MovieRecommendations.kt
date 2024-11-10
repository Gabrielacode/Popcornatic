package com.solt.popcornatic.movies.data.model.MovieDetailPackage.Recommendations


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieRecommendations(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val movieRecommendationsResults: List<MovieRecommendationsResult>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)