package com.solt.popcornatic.tvshows.data.remote.api

import com.solt.popcornatic.TV_SHOWS_PATH
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.AiringTodayTvResult
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.OnAirTvResult
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.PopularTvResult
import com.solt.popcornatic.tvshows.data.remote.model.mainPageResults.TopRatedTvResult
import com.solt.popcornatic.tvshows.data.remote.model.seasonsandepisodes.Season.Episode
import com.solt.popcornatic.tvshows.data.remote.model.seasonsandepisodes.Season.TvShowSeasonDetail
import com.solt.popcornatic.tvshows.data.remote.model.tvShowsDetail.TvShowDetailResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowsApiImpl {
    @GET("$TV_SHOWS_PATH/airing_today")
    suspend fun getAiringTodayTvShows(@Query("page")page:Int): AiringTodayTvResult

    @GET("$TV_SHOWS_PATH/on_the_air")
    suspend fun getOnTheAirTvShows(@Query("page")page:Int): OnAirTvResult

    @GET("$TV_SHOWS_PATH/popular")
    suspend fun getPopularTvShows(@Query("page")page:Int): PopularTvResult

    @GET("$TV_SHOWS_PATH/top_rated")
    suspend fun getTopRatedTvShows(@Query("page")page:Int): TopRatedTvResult
    @GET("$TV_SHOWS_PATH/{tvId}")
    suspend fun  getTVShowsDetailsById(@Path("tvId") tvId:Int):TvShowDetailResult

    @GET("$TV_SHOWS_PATH/{tvId}/season/{season_number}")
    suspend fun  getTVShowSeasonDetail(@Path("tvId") tvId:Int, @Path("season_number")seasonNumber:Int):TvShowSeasonDetail

    @GET("$TV_SHOWS_PATH/{tvId}/season/{season_number}/episode/{episode_number}")
    suspend fun  getTVShowsEpisodeDetail(@Path("tvId") tvId:Int, @Path("season_number")seasonNumber:Int,@Path("episode_number")episodeNumber: Int):Episode




}