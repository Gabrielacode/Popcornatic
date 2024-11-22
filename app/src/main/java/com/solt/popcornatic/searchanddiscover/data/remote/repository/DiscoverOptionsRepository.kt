package com.solt.popcornatic.searchanddiscover.data.remote.repository

import com.solt.popcornatic.ApiResult

interface DiscoverOptionsRepository {
    suspend fun getListOfGenres():ApiResult
}