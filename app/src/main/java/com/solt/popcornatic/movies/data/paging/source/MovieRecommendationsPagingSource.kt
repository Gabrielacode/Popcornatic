package com.solt.popcornatic.movies.data.paging.source


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.MAX_PAGE_NUMBER
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.JointClasses.RecommendedMovies
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Recommendations.MovieRecommendations
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Recommendations.MovieRecommendationsResult
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Similar.MovieSimilar
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Similar.MovieSimilarResult
import com.solt.popcornatic.movies.data.repository.MovieRepositoryImpl

import java.io.IOException

class MovieRecommendationsPagingSource ( val movieId:Int, val startingPageNumber:Int, val movieRepository: MovieRepositoryImpl):PagingSource<Int,RecommendedMovies>() {
    override fun getRefreshKey(state: PagingState<Int, RecommendedMovies>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.nextKey?.minus(1)?:
            state.closestPageToPosition(it)?.prevKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecommendedMovies> {
      val pageKey = params.key?:startingPageNumber

        //Get the similar and recommended movies
        val movieRecommendationsResult = movieRepository.getMovieRecommendations(movieId,pageKey)
        val movieSimilarResult = movieRepository.getSimilarMovies(movieId,pageKey)
        val listofRecommendedMovies :List<RecommendedMovies>
        //If both api worked successfully we combine their lists of movies
        //If one only worked we return only one of them
        //If both didnt work we return an error
       return when{
           movieRecommendationsResult is ApiResult.Success<*> && movieSimilarResult is ApiResult.Success<*> ->{
                val movieRecommendations = movieRecommendationsResult.data as MovieRecommendations
                val similarMovies = movieSimilarResult.data as MovieSimilar
                val list1  = movieRecommendations.movieRecommendationsResults?.map { it.mapToRecommendedMovies() }
                val list2 = similarMovies.movieSimilarResults?.map { it.mapToRecommendedMovies() }
                 when{
                   list1 == null && list2 == null->{
                       return LoadResult.Error(IllegalStateException())
                   }
                    list1 == null && list2 != null ->{
                        listofRecommendedMovies = list2

                        return  LoadResult.Page(listofRecommendedMovies,if((similarMovies.page
                                ?: 0) <= startingPageNumber
                        )null else pageKey-1,if((similarMovies.page
                                ?: 0) >= (similarMovies.totalPages ?: 0)
                        )null else pageKey+1)
                    }
                    list1 != null && list2 == null ->{
                        listofRecommendedMovies = list1

                        return  LoadResult.Page(listofRecommendedMovies,if((movieRecommendations.page
                                ?: 0) <= startingPageNumber
                        )null else pageKey-1,if((movieRecommendations.page
                                ?: 0) >= (movieRecommendations.totalPages ?: 0)
                        )null else pageKey+1)
                    }
                    list1 != null && list2 != null ->{
                        //Make the list unique
                        listofRecommendedMovies = (list2.union(list1)).sortedBy { it.id }.toList()

                        val previousKey = if(pageKey<= startingPageNumber) null else pageKey-1
                        //We should consider them two sources one can finish b4 the other
                        val nextKey =
                            //If both are above the Max page number return null
                           if (movieRecommendations.page!!>= MAX_PAGE_NUMBER && similarMovies.page!!>= MAX_PAGE_NUMBER ){
                               null
                           }else{
                               //If one is higher than the other we get the max page number then compare the page key if it higher than the max we return null
                               if(pageKey >= movieRecommendations.totalPages!!.coerceAtLeast(
                                       similarMovies.totalPages!!
                                   )
                               ){
                                   null
                               } else pageKey+1

                           }

                        LoadResult.Page(listofRecommendedMovies,previousKey,nextKey)
                    }

                     else -> return LoadResult.Error(IllegalStateException())
                 }
            }

           else -> LoadResult.Error(IOException())
       }

       }
    private fun MovieRecommendationsResult?.mapToRecommendedMovies():RecommendedMovies{
        return RecommendedMovies(this?.id?:0,this?.posterPath,this?.backdropPath)
    }
    private fun MovieSimilarResult?.mapToRecommendedMovies():RecommendedMovies{
        return RecommendedMovies(this?.id?:0,this?.posterPath,this?.backdropPath)
    }

}