package com.solt.popcornatic.searchanddiscover.data.remote.models.searchTv


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchTvResult(
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<TvResult>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)