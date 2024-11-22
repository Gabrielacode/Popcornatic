package com.solt.popcornatic.searchanddiscover.data.remote.repository

import com.solt.popcornatic.ApiResult
import com.solt.popcornatic.searchanddiscover.data.remote.api.SearchAndDiscoverApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(val api:SearchAndDiscoverApi):DiscoverRepository {
    override suspend fun discoverMoviesByOptions(page: Int, discoverOptions: Map<String, String>):ApiResult {
        return try{
            val result = api.discoverMoviesByOptions(page,discoverOptions)
            ApiResult.Success(result)
        }catch (e:IOException)  {ApiResult.Failure.NetworkFailure(e)}
        catch (e:HttpException){ApiResult.Failure.ApiFailure(e)}
    }
}