package com.solt.popcornatic.tvshows.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.MAX_PAGE_NUMBER
import com.solt.popcornatic.MIN_PAGE_NUMBER
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.AiringTodayTvResult
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.OnAirTvResult
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.PopularTvResult
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.TopRatedTvResult
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.TvShowMainResult
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.TvShowResult
import com.solt.popcornatic.tvshows.data.remote.repository.TvShowsRepositoryImpl
import kotlin.reflect.KSuspendFunction2

class TvShowsPagingSource(
    val repository:TvShowsRepositoryImpl,
    val apiCall: KSuspendFunction2<TvShowsRepositoryImpl, Int, ApiResult>
):PagingSource<Int,TvShowResult> (){
    override fun getRefreshKey(state: PagingState<Int, TvShowResult>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.nextKey?.minus(1)?:
            state.closestPageToPosition(it)?.prevKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowResult> {
        val pageNumber = params.key?: MIN_PAGE_NUMBER
        val apiResult = apiCall(repository,pageNumber)
        when(apiResult){
            is ApiResult.Failure.ApiFailure -> {
                return LoadResult.Error(apiResult.exception)
            }
            is ApiResult.Failure.NetworkFailure ->  return LoadResult.Error(apiResult.exception)
            is ApiResult.Success<*> ->  {
                val tvShowsApiResult = apiResult.data as TvShowMainResult
                return when(tvShowsApiResult){
                    is AiringTodayTvResult ->   LoadResult.Page(tvShowsApiResult.results?: emptyList(),if(pageNumber<= MIN_PAGE_NUMBER)null else pageNumber-1,if(pageNumber >= (tvShowsApiResult.totalPages
                            ?: 0)
                    )null else if(pageNumber>= MAX_PAGE_NUMBER) null else pageNumber+1)
                    is OnAirTvResult ->  LoadResult.Page(tvShowsApiResult.results?: emptyList(),if(pageNumber<= MIN_PAGE_NUMBER)null else pageNumber-1,if(pageNumber >= (tvShowsApiResult.totalPages
                            ?: 0)
                    )null else if(pageNumber>= MAX_PAGE_NUMBER) null else pageNumber+1)
                    is PopularTvResult ->  LoadResult.Page(tvShowsApiResult.results?: emptyList(),if(pageNumber<= MIN_PAGE_NUMBER)null else pageNumber-1,if(pageNumber >= (tvShowsApiResult.totalPages
                            ?: 0)
                    )null else if(pageNumber>= MAX_PAGE_NUMBER) null else pageNumber+1)
                    is TopRatedTvResult ->  LoadResult.Page(tvShowsApiResult.tvShowResults?: emptyList(),if(pageNumber<= MIN_PAGE_NUMBER)null else pageNumber-1,if(pageNumber >= (tvShowsApiResult.totalPages
                            ?: 0)
                    )null else if(pageNumber>= MAX_PAGE_NUMBER) null else pageNumber+1)
                }
            }
        }
    }
}