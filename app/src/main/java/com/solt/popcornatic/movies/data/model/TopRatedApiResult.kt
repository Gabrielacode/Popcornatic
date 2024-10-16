package com.solt.popcornatic.movies.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TopRatedApiResult(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
):MovieApiResult