package com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Recommendations


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowRecommendations(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<TvShowRecommendationResult?>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)