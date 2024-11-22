package com.solt.popcornatic.searchanddiscover.domain

import com.solt.popcornatic.searchanddiscover.data.remote.paging.SearchResultsType
import com.solt.popcornatic.searchanddiscover.data.remote.repository.SearchPagedRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(val pagedRepository: SearchPagedRepository) {
    fun search(query:String,searchResultsType: SearchResultsType) = pagedRepository.search(query,searchResultsType).flow
}