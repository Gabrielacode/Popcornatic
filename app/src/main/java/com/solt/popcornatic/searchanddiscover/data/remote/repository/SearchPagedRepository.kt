package com.solt.popcornatic.searchanddiscover.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.solt.popcornatic.searchanddiscover.data.remote.paging.SearchPagingSource
import com.solt.popcornatic.searchanddiscover.data.remote.paging.SearchResultsType
import retrofit2.http.Query
import javax.inject.Inject

class SearchPagedRepository @Inject constructor(val repository: SearchRepositoryImpl) {
    //We made the search for movie and tv shows into the same paging source so that we can search them both at the same time
    fun search(query: String,searchResultsType: SearchResultsType) = Pager(PagingConfig(10)){SearchPagingSource(query,repository,searchResultsType)}
}