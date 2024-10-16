package com.solt.popcornatic.movies.data.model

import com.solt.popcornatic.movies.data.api.MovieApi
import kotlinx.serialization.Serializable

@Serializable
data class PopularApiResult(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
):MovieApiResult