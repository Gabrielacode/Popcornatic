package com.solt.popcornatic.searchanddiscover.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.MAX_PAGE_NUMBER
import com.solt.popcornatic.MIN_PAGE_NUMBER
import com.solt.popcornatic.searchanddiscover.data.remote.models.SearchItem
import com.solt.popcornatic.searchanddiscover.data.remote.models.searchMovie.SearchMovieResult
import com.solt.popcornatic.searchanddiscover.data.remote.models.toSearchItem
import com.solt.popcornatic.searchanddiscover.data.remote.repository.DiscoverRepositoryImpl
import com.solt.popcornatic.searchanddiscover.ui.viewmodels.DiscoverOptions

class DiscoverPagingSource(val repositoryImpl: DiscoverRepositoryImpl,val searchResultsType: SearchResultsType,val discoverOptions :Map< out DiscoverOptions.Options,DiscoverOptions>):PagingSource<Int, SearchItem> (){
    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.nextKey?.minus(1)?:
            state.closestPageToPosition(it)?.prevKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val pageNumber = params.key?: MIN_PAGE_NUMBER

     return  when(searchResultsType){
            SearchResultsType.MOVIES_ONLY -> {
                val queryMap = discoverOptions.mapKeys { it.key.toQuery() }.mapValues { it.value.toQuery() }
                val result = repositoryImpl.discoverMoviesByOptions(pageNumber,queryMap)
                when(result){
                    is ApiResult.Failure.ApiFailure -> LoadResult.Error(result.exception)
                    is ApiResult.Failure.NetworkFailure -> LoadResult.Error(result.exception)
                    is ApiResult.Success<*> -> {
                        val data = result.data as SearchMovieResult
                        LoadResult.Page(data.results?.map { it.toSearchItem() }?: emptyList(),if(pageNumber<= MIN_PAGE_NUMBER)null else pageNumber-1,if (pageNumber>=data.totalPages?:0)null else if(pageNumber>= MAX_PAGE_NUMBER)null else pageNumber+1)
                    }
                }

            }
            SearchResultsType.TVSHOWS_ONLY -> {
                val queryMap = discoverOptions.mapKeys { it.key.toQuery() }.mapValues { it.value.toQuery() }
                val result = repositoryImpl.discoverMoviesByOptions(pageNumber,queryMap)
                when(result){
                    is ApiResult.Failure.ApiFailure -> LoadResult.Error(result.exception)
                    is ApiResult.Failure.NetworkFailure -> LoadResult.Error(result.exception)
                    is ApiResult.Success<*> -> {
                        val data = result.data as SearchMovieResult
                        LoadResult.Page(data.results?.map { it.toSearchItem() }?: emptyList(),if(pageNumber<= MIN_PAGE_NUMBER)null else pageNumber-1,if (pageNumber>=data.totalPages?:0)null else if(pageNumber>= MAX_PAGE_NUMBER)null else pageNumber+1)
                    }
                }
            }
            SearchResultsType.BOTH -> {
                LoadResult.Page(emptyList(),null,null)
            }
        }
    }
}