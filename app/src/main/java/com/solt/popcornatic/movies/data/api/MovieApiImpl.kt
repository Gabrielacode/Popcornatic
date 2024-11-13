package com.solt.popcornatic.movies.data.api

import com.solt.popcornatic.MOVIES_PATH
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Credits.MovieDetailCredits
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Images.MovieDetailImages
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.MovieDetailResult
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.ProductionCompanies.ProductionCompanyDetail
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Recommendations.MovieRecommendations
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Similar.MovieSimilar
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.Videos.MovieDetailVideos
import com.solt.popcornatic.movies.data.model.PopularApiResult
import com.solt.popcornatic.movies.data.model.TopRatedApiResult
import com.solt.popcornatic.movies.data.model.TrendingApiResult
import com.solt.popcornatic.movies.data.model.UpcomingApiResult
import retrofit2.http.GET
import retrofit2.http.Path
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

     @GET("$MOVIES_PATH/{movieId}")
     suspend fun getMovieDetailOfMovieById(@Path("movieId") movieId:Int  ): MovieDetailResult
     @GET("$MOVIES_PATH/{movieId}/recommendations")
     suspend fun getMovieRecommendations(@Path("movieId")movieId: Int, @Query("page")page:Int):MovieRecommendations
    @GET("$MOVIES_PATH/{movieId}/similar")
    suspend fun getSimilarMovies(@Path("movieId")movieId: Int,@Query("page")page:Int):MovieSimilar

    @GET("company/{companyId}")
    suspend fun getProductionCompanyDetailsById(@Path("companyId")companyId:Int): ProductionCompanyDetail
    @GET("$MOVIES_PATH/{movieId}/images")
    suspend fun getImagesForMovieById(@Path("movieId")movieId:Int):MovieDetailImages
    @GET("$MOVIES_PATH/{movieId}/credits")
    suspend fun getCreditsforMovieById(@Path("movieId")movieId: Int):MovieDetailCredits
    @GET("$MOVIES_PATH/{movieId}/videos")
    suspend fun getVideosForMovieById(@Path("movieId")movieId: Int):MovieDetailVideos

}