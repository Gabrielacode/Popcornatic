package com.solt.popcornatic.searchanddiscover.data.remote.repository

import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.searchanddiscover.data.remote.api.DiscoverOptionsApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DiscoverOptionRepositoryImpl @Inject constructor(val api: DiscoverOptionsApi):DiscoverOptionsRepository {
    override suspend fun getListOfGenres(): ApiResult {
        return try {
            val result = api.getMoviesGenreList()
            ApiResult.Success(result)
        }catch (e: IOException){
            ApiResult.Failure.NetworkFailure(e)
        }catch (e:HttpException){
            ApiResult.Failure.ApiFailure(e)
        }
    }

}