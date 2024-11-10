package com.solt.popcornatic.movies.data.model.MovieDetailPackage.Reviews


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviews(
    @SerialName("id")
    val id: Int?,
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val movieReviewsResults: List<MovieReviewsResult?>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)