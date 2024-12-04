package com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Similar


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowSimilar(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<TvShowSimilarResult?>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)