package com.solt.popcornatic.searchanddiscover.data.remote.repository

import com.solt.popcornatic.ApiResult

interface DiscoverRepository {
    suspend fun discoverMoviesByOptions(page:Int,discoverOptions:Map<String,String>):ApiResult
}