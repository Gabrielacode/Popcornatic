package com.solt.popcornatic.searchanddiscover.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.solt.popcornatic.searchanddiscover.data.remote.paging.DiscoverPagingSource
import com.solt.popcornatic.searchanddiscover.data.remote.paging.SearchResultsType
import com.solt.popcornatic.searchanddiscover.ui.viewmodels.DiscoverOptions
import javax.inject.Inject

class DiscoverPagedRepository @Inject constructor(val discoverRepositoryImpl: DiscoverRepositoryImpl) {
    fun discover(searchResultsType: SearchResultsType,discoverOptions:Map< out DiscoverOptions.Options,DiscoverOptions>)= Pager(PagingConfig(10)){DiscoverPagingSource(discoverRepositoryImpl,searchResultsType,discoverOptions)}.flow
}