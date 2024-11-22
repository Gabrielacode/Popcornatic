package com.solt.popcornatic.searchanddiscover.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.MAX_PAGE_NUMBER
import com.solt.popcornatic.MIN_PAGE_NUMBER
import com.solt.popcornatic.searchanddiscover.data.remote.models.SearchItem
import com.solt.popcornatic.searchanddiscover.data.remote.models.searchMovie.MovieResult
import com.solt.popcornatic.searchanddiscover.data.remote.models.searchMovie.SearchMovieResult
import com.solt.popcornatic.searchanddiscover.data.remote.models.searchTv.SearchTvResult
import com.solt.popcornatic.searchanddiscover.data.remote.models.toSearchItem
import com.solt.popcornatic.searchanddiscover.data.remote.repository.SearchRepositoryImpl
import java.io.IOException

enum class SearchResultsType {
     MOVIES_ONLY,TVSHOWS_ONLY,BOTH
 }
class SearchPagingSource(val query:String,val searchRepositoryImpl: SearchRepositoryImpl,val searchResultsType: SearchResultsType):PagingSource<Int,SearchItem>() {
    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.nextKey?.minus(1)?:
            state.closestPageToPosition(it)?.prevKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
       val pageNumber = params.key?: MIN_PAGE_NUMBER
         return when(searchResultsType){
            SearchResultsType.MOVIES_ONLY -> {
               val searchResult = searchRepositoryImpl.searchMovies(query,pageNumber)
               when(searchResult){
                   is ApiResult.Failure.ApiFailure ->  LoadResult.Error(searchResult.exception)
                   is ApiResult.Failure.NetworkFailure -> LoadResult.Error(searchResult.exception)
                   is ApiResult.Success<*> -> {
                      val data = searchResult.data as SearchMovieResult
                        LoadResult.Page(data.results?.map { it.toSearchItem() }?: emptyList(),if(pageNumber<= MIN_PAGE_NUMBER)null else pageNumber-1,if (pageNumber>=data.totalPages?:0)null else if(pageNumber>= MAX_PAGE_NUMBER)null else pageNumber+1)
                   }
               }
            }
            SearchResultsType.TVSHOWS_ONLY -> {
                val searchResult = searchRepositoryImpl.searchTvShows(query,pageNumber)
                when(searchResult){
                    is ApiResult.Failure.ApiFailure ->  LoadResult.Error(searchResult.exception)
                    is ApiResult.Failure.NetworkFailure -> LoadResult.Error(searchResult.exception)
                    is ApiResult.Success<*> -> {
                        val data = searchResult.data as SearchTvResult
                        LoadResult.Page(data.results?.map { it.toSearchItem() }?: emptyList(),if(pageNumber<= MIN_PAGE_NUMBER)null else pageNumber-1,if (pageNumber>=data.totalPages?:0)null else if(pageNumber>= MAX_PAGE_NUMBER)null else pageNumber+1)
                    }
                }
            }
            SearchResultsType.BOTH -> {
                val movieSearchResult = searchRepositoryImpl.searchMovies(query,pageNumber)
                val tvShowsSearchResult = searchRepositoryImpl.searchTvShows(query,pageNumber)
                return when{
                    movieSearchResult is ApiResult.Success<*> && tvShowsSearchResult is ApiResult.Success<*>->{
                        val listOfMovieSearchItems = (movieSearchResult.data as SearchMovieResult).results?.map { it.toSearchItem() }?: emptyList()
                        val listOfTvShowSearchItems = (tvShowsSearchResult.data as SearchTvResult).results?.map { it.toSearchItem() }?: emptyList()
                        val totalList = (listOfTvShowSearchItems + listOfMovieSearchItems)
                        LoadResult.Page(totalList,if(pageNumber<= MIN_PAGE_NUMBER)null else pageNumber-1,if (pageNumber>=Math.max(movieSearchResult.data.totalPages?:0,tvShowsSearchResult.data.totalPages?:0))null else if(pageNumber>= MAX_PAGE_NUMBER)null else pageNumber+1)

                    }
                    else -> LoadResult.Error(IOException())
                }
            }
        }
    }
}