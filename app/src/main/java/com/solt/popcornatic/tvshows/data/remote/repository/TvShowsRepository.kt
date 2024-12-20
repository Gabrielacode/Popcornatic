package com.solt.popcornatic.tvshows.data.remote.repository

import com.solt.popcornatic.ApiResult
import retrofit2.http.Query

interface TvShowsRepository {
    suspend fun getTvShowsAiringToday(page:Int):ApiResult
    suspend fun getTvShowsOnTheAir(page:Int):ApiResult

    suspend fun getPopularTvShows(page:Int):ApiResult

    suspend fun getTopRatedTvShows(page:Int):ApiResult
    suspend fun getTvShowById(tvShowId:Int):ApiResult

    suspend fun getTvShowSeason(tvShowId: Int,seasonNumber: Int):ApiResult
    suspend fun getTvShowEpisode(tvShowId: Int,seasonNumber: Int,episodeNumber:Int):ApiResult

    suspend fun getRecommendedTvShows(tvShowId: Int,page: Int):ApiResult
    suspend fun getSimilarTvShows(tvShowId: Int,page: Int):ApiResult

    suspend fun getTvShowsProductionCompanyDetailsById(companyId:Int):ApiResult

    suspend fun getTvShowImages(tvShowId: Int):ApiResult
    suspend fun getTvShowVideos(tvShowId: Int):ApiResult
    suspend fun getTvShowCredits(tvShowId: Int):ApiResult

}