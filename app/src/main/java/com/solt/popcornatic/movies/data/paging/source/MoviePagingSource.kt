package com.solt.popcornatic.movies.data.paging.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.movies.data.model.MovieApiResult
import com.solt.popcornatic.movies.data.model.MovieResult
import com.solt.popcornatic.movies.data.model.PopularApiResult
import com.solt.popcornatic.movies.data.model.TopRatedApiResult
import com.solt.popcornatic.movies.data.model.TrendingApiResult
import com.solt.popcornatic.movies.data.model.UpcomingApiResult
import com.solt.popcornatic.movies.data.repository.MovieRepositoryImpl
import kotlin.reflect.KSuspendFunction3

class MoviePagingSource(val startingPage:Int,
                        val movieRepositoryImpl: MovieRepositoryImpl,
                        val apiCall: KSuspendFunction3<MovieRepositoryImpl, Int, String?, ApiResult>
) :PagingSource<Int,MovieResult>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResult>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.nextKey?.minus(1)?:
            state.closestPageToPosition(it)?.prevKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult> {
      val pageNumber = params.key?:startingPage
      val apiResult = apiCall.invoke(movieRepositoryImpl,pageNumber,null)
      when(apiResult){
          is ApiResult.Failure.ApiFailure -> {
              return LoadResult.Error(apiResult.exception)
          }
          is ApiResult.Failure.NetworkFailure ->  return LoadResult.Error(apiResult.exception)
          is ApiResult.Success<*> ->  {
               val movieApiResult = apiResult.data as MovieApiResult
                return when(movieApiResult){
                   is PopularApiResult ->
                      LoadResult.Page(movieApiResult.results,if(pageNumber<=startingPage)null else pageNumber-1,if(pageNumber>=movieApiResult.total_pages)null else pageNumber+1)

                   is TrendingApiResult ->
                        LoadResult.Page(movieApiResult.results,if(pageNumber<=startingPage)null else pageNumber-1,if(pageNumber>=movieApiResult.total_pages)null else pageNumber+1)


                   is TopRatedApiResult -> LoadResult.Page(movieApiResult.results,if(pageNumber<=startingPage)null else pageNumber-1,if(pageNumber>=movieApiResult.total_pages)null else pageNumber+1)
                   is UpcomingApiResult -> LoadResult.Page(movieApiResult.results,if(pageNumber<=startingPage)null else pageNumber-1,if(pageNumber>=movieApiResult.total_pages)null else pageNumber+1)
               }
          }
      }

    }
}