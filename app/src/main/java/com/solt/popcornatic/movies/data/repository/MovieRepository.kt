package com.solt.popcornatic.movies.data.repository

import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.AppendToResponseOptions
import com.solt.popcornatic.movies.data.model.TrendingApiResult
import org.intellij.lang.annotations.Language

interface MovieRepository {
    suspend fun getTrendingMovies(page:Int, language: String?):ApiResult
    suspend fun getPopularMovies(page: Int,language: String?):ApiResult
    suspend fun getUpcomingMovies(page: Int,language: String?):ApiResult
    suspend fun getTopRatedMovies(page: Int,language: String?):ApiResult
    suspend fun getMovieDetailsById(movieId:Int):ApiResult //Images , Videos and Credits will be fetched separately
    suspend fun getMovieRecommendations(movieId: Int,page: Int):ApiResult
    suspend fun getSimilarMovies(movieId: Int,page: Int):ApiResult
    suspend fun getProductionCompanyDetailsById(companyId:Int):ApiResult
    suspend fun getMovieImagesById(movieId:Int):ApiResult
    suspend fun getMovieCreditsById(movieId: Int):ApiResult
}