package com.solt.popcornatic.searchanddiscover.data.remote.repository

import com.solt.popcornatic.ApiResult
import retrofit2.http.Query

interface SearchRepository {
    suspend fun searchMovies(query: String,page:Int):ApiResult
    suspend fun searchTvShows(query: String,page:Int):ApiResult
}