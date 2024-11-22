package com.solt.popcornatic.searchanddiscover.data.remote.api

import com.solt.popcornatic.DISCOVER_PATH
import com.solt.popcornatic.SEARCH_PATH
import com.solt.popcornatic.searchanddiscover.data.remote.models.searchMovie.MovieResult
import com.solt.popcornatic.searchanddiscover.data.remote.models.searchMovie.SearchMovieResult
import com.solt.popcornatic.searchanddiscover.data.remote.models.searchTv.SearchTvResult
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.QueryName
import java.lang.reflect.Type

interface  SearchAndDiscoverApi {
    @GET("$SEARCH_PATH/movie")
    suspend fun searchMovies(@Query("query") query:String ,@Query("page")page:Int):SearchMovieResult

    @GET("$SEARCH_PATH/tv")
    suspend fun searchTvShows(@Query("query") query:String ,@Query("page")page:Int): SearchTvResult

    @GET("$DISCOVER_PATH/movie")
    suspend fun discoverMoviesByOptions( @Query("page") page:Int,@QueryMap  discoverOptions:Map<String,String> ):SearchMovieResult


}
