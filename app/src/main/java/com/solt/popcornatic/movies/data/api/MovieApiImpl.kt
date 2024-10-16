package com.solt.popcornatic.movies.data.api

import com.solt.popcornatic.MOVIES_PATH
import com.solt.popcornatic.movies.data.model.PopularApiResult
import com.solt.popcornatic.movies.data.model.TopRatedApiResult
import com.solt.popcornatic.movies.data.model.TrendingApiResult
import com.solt.popcornatic.movies.data.model.UpcomingApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiImpl {
    @GET("$MOVIES_PATH/now_playing")
    suspend fun getTrendingMovies(@Query("page")page:Int ,@Query("language")language:String):TrendingApiResult


     @GET("$MOVIES_PATH/upcoming")
     suspend fun getUpcomingMovies(@Query("page")page:Int ,@Query("language")language:String):UpcomingApiResult


     @GET("$MOVIES_PATH/top_rated")
     suspend fun getTopRatedMovies(@Query("page")page:Int ,@Query("language")language:String):TopRatedApiResult

     @GET("$MOVIES_PATH/popular")
     suspend fun getPopularMovies(@Query("page")page:Int ,@Query("language")language:String): PopularApiResult


}