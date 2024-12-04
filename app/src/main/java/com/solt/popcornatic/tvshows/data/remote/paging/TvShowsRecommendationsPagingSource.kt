package com.solt.popcornatic.tvshows.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.MAX_PAGE_NUMBER
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Similar.MovieSimilar
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.JointClasses.RecommendedTvShows
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Recommendations.TvShowRecommendationResult
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Recommendations.TvShowRecommendations
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Similar.TvShowSimilar
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.Similar.TvShowSimilarResult
import com.solt.popcornatic.tvshows.data.remote.repository.TvShowsRepository
import java.io.IOException

class TvShowsRecommendationsPagingSource(val tvShowId:Int,val startingPageNumber :Int, val tvShowsRepository: TvShowsRepository):PagingSource<Int,RecommendedTvShows>() {
    override fun getRefreshKey(state: PagingState<Int, RecommendedTvShows>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.nextKey?.minus(1)?:
            state.closestPageToPosition(it)?.prevKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecommendedTvShows> {
        val pageKey = params.key?:startingPageNumber

        //Get the similar and recommended movies
        val tvShowRecommendationResult = tvShowsRepository.getRecommendedTvShows(tvShowId,pageKey)
        val tvShowSimilarResult = tvShowsRepository.getSimilarTvShows(tvShowId,pageKey)
        val listofRecommendedTvShows :List<RecommendedTvShows>
        //If both api worked successfully we combine their lists of movies
        //If one only worked we return only one of them
        //If both didnt work we return an error
        return when{
             tvShowRecommendationResult is ApiResult.Success<*> && tvShowSimilarResult is ApiResult.Success<*> ->{
                val tvShowRecommendations = tvShowRecommendationResult.data as TvShowRecommendations
                val tvShowSimilar = tvShowSimilarResult.data as TvShowSimilar
                val list1  = tvShowRecommendations.results?.map { it.mapToRecommendedTvShows() }
                val list2 = tvShowSimilar.results?.map { it.mapToRecommendedTvShows() }
                when{
                    list1 == null && list2 == null->{
                        return LoadResult.Error(IllegalStateException())
                    }
                    list1 == null && list2 != null ->{
                        listofRecommendedTvShows = list2

                        return  LoadResult.Page(listofRecommendedTvShows,if((tvShowSimilar.page
                                ?: 0) <= startingPageNumber
                        )null else pageKey-1,if((tvShowSimilar.page
                                ?: 0) >= (tvShowSimilar.totalPages ?: 0)
                        )null else pageKey+1)
                    }
                    list1 != null && list2 == null ->{
                        listofRecommendedTvShows = list1

                        return  LoadResult.Page(listofRecommendedTvShows,if((tvShowRecommendations.page
                                ?: 0) <= startingPageNumber
                        )null else pageKey-1,if((tvShowRecommendations.page
                                ?: 0) >= (tvShowRecommendations.totalPages ?: 0)
                        )null else pageKey+1)
                    }
                    list1 != null && list2 != null ->{
                        //Make the list unique
                        listofRecommendedTvShows = (list2.union(list1)).sortedBy { it.id }.toList()

                        val previousKey = if(pageKey<= startingPageNumber) null else pageKey-1
                        //We should consider them two sources one can finish b4 the other
                        val nextKey =
                            //If both are above the Max page number return null
                            if (tvShowRecommendations.page!!>= MAX_PAGE_NUMBER && tvShowSimilar.page!!>= MAX_PAGE_NUMBER ){
                                null
                            }else{
                                //If one is higher than the other we get the max page number then compare the page key if it higher than the max we return null
                                if(pageKey >= tvShowRecommendations.totalPages!!.coerceAtLeast(
                                        tvShowSimilar.totalPages!!
                                    )
                                ){
                                    null
                                } else pageKey+1

                            }

                        LoadResult.Page(listofRecommendedTvShows,previousKey,nextKey)
                    }

                    else -> return LoadResult.Error(IllegalStateException())
                }
            }

            else -> LoadResult.Error(IOException())
        }

    }
    private fun TvShowRecommendationResult?.mapToRecommendedTvShows():RecommendedTvShows{
        return RecommendedTvShows(this?.id?:0,this?.posterPath,this?.backdropPath)
    }
        private fun TvShowSimilarResult?.mapToRecommendedTvShows():RecommendedTvShows{
        return RecommendedTvShows(this?.id?:0,this?.posterPath,this?.backdropPath)
    }
}