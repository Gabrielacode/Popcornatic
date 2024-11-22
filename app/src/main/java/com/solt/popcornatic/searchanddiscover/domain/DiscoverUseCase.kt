package com.solt.popcornatic.searchanddiscover.domain

import com.solt.popcornatic.searchanddiscover.data.remote.paging.SearchResultsType
import com.solt.popcornatic.searchanddiscover.data.remote.repository.DiscoverPagedRepository
import com.solt.popcornatic.searchanddiscover.ui.viewmodels.DiscoverOptions
import javax.inject.Inject

class DiscoverUseCase @Inject constructor(val discoverPagedRepository: DiscoverPagedRepository)
{
    fun discover(searchResultsType: SearchResultsType, discoverOptions: Map<out DiscoverOptions.Options, DiscoverOptions>) = discoverPagedRepository.discover(searchResultsType,discoverOptions)
}