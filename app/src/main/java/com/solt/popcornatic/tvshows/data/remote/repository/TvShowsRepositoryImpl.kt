package com.solt.popcornatic.tvshows.data.remote.repository

import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.tvshows.data.remote.api.TvShowsApiImpl
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TvShowsRepositoryImpl @Inject constructor(val tvShowsApi: TvShowsApiImpl):TvShowsRepository {
    override suspend fun getTvShowsAiringToday(page:Int): ApiResult {

            return  try {
             val result =   tvShowsApi.getAiringTodayTvShows(page)
             ApiResult.Success(result)
            }catch (e: IOException){
                ApiResult.Failure.NetworkFailure(e)
            }catch (e: HttpException){
                ApiResult.Failure.ApiFailure(e)
            }

    }

    override suspend fun getTvShowsOnTheAir(page:Int): ApiResult {
        return  try {
            val result = tvShowsApi.getOnTheAirTvShows(page)
            ApiResult.Success(result)
        }catch (e: IOException){
            ApiResult.Failure.NetworkFailure(e)
        }catch (e: HttpException){
            ApiResult.Failure.ApiFailure(e)
        }
    }

    override suspend fun getPopularTvShows(page: Int): ApiResult {
        return  try {
            val result = tvShowsApi.getPopularTvShows(page)
            ApiResult.Success(result)
        }catch (e: IOException){
            ApiResult.Failure.NetworkFailure(e)
        }catch (e: HttpException){
            ApiResult.Failure.ApiFailure(e)
        }
    }

    override suspend fun getTopRatedTvShows(page:Int): ApiResult {
        return  try {
            val result =  tvShowsApi.getTopRatedTvShows(page)
            ApiResult.Success(result)
        }catch (e: IOException){
            ApiResult.Failure.NetworkFailure(e)
        }catch (e: HttpException){
            ApiResult.Failure.ApiFailure(e)
        }
    }

    override suspend fun getTvShowById(tvShowId: Int): ApiResult {
        return  try {
            val result = tvShowsApi.getTVShowsDetailsById(tvShowId)
            ApiResult.Success(result)
        }catch (e: IOException){
            ApiResult.Failure.NetworkFailure(e)
        }catch (e: HttpException){
            ApiResult.Failure.ApiFailure(e)
        }
    }

    override suspend fun getTvShowSeason(tvShowId: Int, seasonNumber: Int): ApiResult {
        return  try {
            val result = tvShowsApi.getTVShowSeasonDetail(tvShowId,seasonNumber)
            ApiResult.Success(result)
        }catch (e: IOException){
            ApiResult.Failure.NetworkFailure(e)
        }catch (e: HttpException){
            ApiResult.Failure.ApiFailure(e)
        }
    }

    override suspend fun getTvShowEpisode(tvShowId: Int, seasonNumber: Int,episodeNumber:Int): ApiResult {
        return  try {
            val result = tvShowsApi.getTVShowsEpisodeDetail(tvShowId,seasonNumber,episodeNumber)
            ApiResult.Success(result)
        }catch (e: IOException){
            ApiResult.Failure.NetworkFailure(e)
        }catch (e: HttpException){
            ApiResult.Failure.ApiFailure(e)
        }
    }
}