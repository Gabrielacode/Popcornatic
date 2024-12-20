package com.solt.popcornatic.searchanddiscover.data.remote.models.searchMovie


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMovieResult(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<MovieResult>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)