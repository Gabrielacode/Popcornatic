package com.solt.popcornatic.tvshows.data.remote.model.mainPageResults


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopRatedTvResult(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val tvShowResults: List<TvShowResult>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
):TvShowMainResult