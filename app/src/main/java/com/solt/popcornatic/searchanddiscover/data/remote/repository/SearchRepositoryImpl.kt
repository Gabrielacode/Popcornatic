package com.solt.popcornatic.searchanddiscover.data.remote.repository

import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.searchanddiscover.data.remote.api.SearchAndDiscoverApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(val searchApi:SearchAndDiscoverApi):SearchRepository {
    override  suspend fun searchMovies(query: String, page: Int): ApiResult {
        return try {
         val result = searchApi.searchMovies(query,page)
         ApiResult.Success(result)
        }catch (e:IOException){ApiResult.Failure.NetworkFailure(e)}
        catch (e:HttpException){ApiResult.Failure.ApiFailure(e)}
    }

    override  suspend fun  searchTvShows(query: String, page: Int): ApiResult {
        return try {
            val result = searchApi.searchTvShows(query,page)
            ApiResult.Success(result)
        }catch (e:IOException){ApiResult.Failure.NetworkFailure(e)}
        catch (e:HttpException){ApiResult.Failure.ApiFailure(e)}
    }
}